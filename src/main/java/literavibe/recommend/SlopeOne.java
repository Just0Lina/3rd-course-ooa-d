package literavibe.recommend;

import literavibe.model.dto.DateToYearConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import literavibe.model.dto.BookDto;
import literavibe.model.entities.Mark;
import literavibe.model.entities.Book;
import literavibe.model.entities.User;
import literavibe.model.exceptions.NotFoundException;
import literavibe.respository.MarkRepository;
import literavibe.respository.BookRepository;
import literavibe.respository.UserRepository;
import literavibe.security.domain.JwtAuthentication;
import literavibe.utils.FindUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.StreamSupport;

import static literavibe.security.service.impl.AuthServiceCommon.getAuthInfo;

/**
 * Slope One algorithm implementation
 */
@Service
public class SlopeOne {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final MarkRepository markRepository;
    private final Map<Book, Map<Book, Double>> diff = new HashMap<>();
    private final Map<Book, Map<Book, Integer>> freq = new HashMap<>();
    private final Map<User, HashMap<Book, Double>> outputData = new HashMap<>();
    private final BookRepository bookRepository;
    private Integer limit;

    @Autowired
    public SlopeOne(ModelMapper mapper, UserRepository userRepository, MarkRepository markRepository,
                    BookRepository bookRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.markRepository = markRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookDto> recommendAlgSlopeOne(Integer limit) throws NotFoundException {
        this.limit = (limit == null) ? 100 : limit;
        Map<User, HashMap<Book, Double>> inputData = initializeData();
        buildDifferencesMatrix(inputData);
        return predict(inputData);
    }

    private Map<User, HashMap<Book, Double>> initializeData() {
        List<Mark> markList = StreamSupport.stream(markRepository.findAll().spliterator(), false).toList();
        Map<User, HashMap<Book, Double>> data = new HashMap<>();
        for (Mark m : markList) {
            HashMap<Book, Double> userBooksMarked;
            if (data.containsKey(m.getUser())) {
                userBooksMarked = data.get(m.getUser());
            } else {
                userBooksMarked = new HashMap<>();
            }
            userBooksMarked.put(m.getBook(), (double) m.getMark());
            data.put(m.getUser(), userBooksMarked);
        }
        return data;
    }

    /**
     * Based on the available data, calculate the relationships between the
     * items and number of occurences
     *
     * @param data existing user data and their items' ratings
     */
    private void buildDifferencesMatrix(Map<User, HashMap<Book, Double>> data) {
        data.values().forEach(user -> {
            user.forEach((book1, rating1) -> {
                if (!diff.containsKey(book1)) {
                    diff.put(book1, new HashMap<>());
                    freq.put(book1, new HashMap<>());
                }
                user.forEach((book2, rating2) -> {
                    int oldCount = freq.get(book1).getOrDefault(book2, 0);
                    double oldDiff = diff.get(book1).getOrDefault(book2, 0.0);
                    double observedDiff = rating1 - rating2;
                    freq.get(book1).put(book2, oldCount + 1);
                    diff.get(book1).put(book2, oldDiff + observedDiff);
                });
            });
        });

        diff.forEach((book1, innerMap) -> {
            innerMap.forEach((book2, value) -> {
                int count = freq.get(book1).get(book2);
                innerMap.put(book2, value / count);
            });
        });
    }

    /**
     * Based on existing data predict all missing ratings. If prediction is not
     * possible, the value will be equal to -1
     *
     * @param data  existing user data and their items' ratings
     */
    private List<BookDto> predict(Map<User, HashMap<Book, Double>> data) throws NotFoundException {
        // Initialize the uPred and uFreq maps
        HashMap<Book, Double> uPred = new HashMap<>();
        HashMap<Book, Integer> uFreq = new HashMap<>();
        diff.keySet().forEach(j -> {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        });

        for (Entry<User, HashMap<Book, Double>> e : data.entrySet()) {
            updateUPredAndUFreq(e, uPred, uFreq);
            HashMap<Book, Double> clean = calculateCleanMap(uPred, uFreq);
            fillMissingRatings(e, clean);
            outputData.put(e.getKey(), clean);
        }

        return getSortedBookDtos(limit);
    }

    private void updateUPredAndUFreq(Entry<User, HashMap<Book, Double>> e,
                                     HashMap<Book, Double> uPred, HashMap<Book, Integer> uFreq) {
        for (Book j : e.getValue().keySet()) {
            for (Book k : diff.keySet()) {
                double diffValue = diff.getOrDefault(k, Collections.emptyMap()).getOrDefault(j, 0.0);
                double userValue = e.getValue().getOrDefault(j, 0.0);
                int freqValue = freq.getOrDefault(k, Collections.emptyMap()).getOrDefault(j, 0);

                double predictedValue = diffValue + userValue;
                double finalValue = predictedValue * freqValue;
                uPred.put(k, uPred.getOrDefault(k, 0.0) + finalValue);
                uFreq.put(k, uFreq.getOrDefault(k, 0) + freqValue);
            }
        }
    }

    private HashMap<Book, Double> calculateCleanMap(HashMap<Book, Double> uPred, HashMap<Book, Integer> uFreq) {
        HashMap<Book, Double> clean = new HashMap<>();
        uPred.keySet().forEach(j -> {
            if (uFreq.get(j) > 0) {
                clean.put(j, uPred.get(j) / uFreq.get(j));
            }
        });
        return clean;
    }

    private void fillMissingRatings(Entry<User, HashMap<Book, Double>> e, HashMap<Book, Double> clean) {
        List<Mark> markList = StreamSupport.stream(markRepository.findAll().spliterator(), false).toList();
        markList.forEach(j -> {
            Book book = j.getBook();
            if (e.getValue().containsKey(book)) {
                clean.put(book, e.getValue().get(book));
            } else if (!clean.containsKey(book)) {
                clean.put(book, -1.0);
            }
        });
    }

    private List<BookDto> getSortedBookDtos(Integer limit) throws NotFoundException {
        List<BookDto> bookDtos;
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            JwtAuthentication principal = getAuthInfo();
            User user = FindUtils.findUser(userRepository, principal.getLogin());
            HashMap<Book, Double> outputUserData = outputData.get(user);
            if (outputUserData != null) {
                List<Book> sortedList = outputUserData.entrySet().stream()
                        .sorted(Map.Entry.<Book, Double>comparingByValue().reversed())
                        .limit(limit)
                        .map(Map.Entry::getKey)
                        .toList();
                bookDtos = mapper.map(sortedList, new TypeToken<List<BookDto>>() {
                }.getType());
            } else {
                bookDtos = mapper.map(bookRepository.findTopBooksWithLimit(limit), new TypeToken<List<BookDto>>() {
                }.getType());
            }
        } else {
            mapper.addConverter(new DateToYearConverter());
            bookDtos = mapper.map(bookRepository.findTopBooksWithLimit(limit), new TypeToken<List<BookDto>>() {
            }.getType());
        }

        List<BookDto> books = mapper.map(bookRepository.findRandomWithLimit(limit - bookDtos.size()), new TypeToken<List<BookDto>>() {
        }.getType());
        bookDtos.addAll(books);
        return bookDtos;
    }


}

