package literavibe.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.MarkDto;
import literavibe.model.entities.Mark;
import literavibe.model.entities.MarkKey;
import literavibe.model.entities.Book;
import literavibe.model.entities.User;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.respository.MarkRepository;
import literavibe.respository.BookRepository;
import literavibe.respository.UserRepository;
import literavibe.security.service.impl.AuthServiceCommon;
import literavibe.services.MarkService;
import literavibe.utils.FindUtils;

import java.util.Optional;

import static literavibe.security.service.impl.AuthServiceCommon.checkAuthorities;

@Service

public class MarkServiceImpl implements MarkService {
    private final ModelMapper mapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final MarkRepository markRepository;

    @Autowired
    public MarkServiceImpl(ModelMapper mapper, BookRepository bookRepository, UserRepository userRepository,
                           MarkRepository markRepository) {
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.markRepository = markRepository;
    }

    private void setBookToMark(Mark mark, Long bookId) throws NotFoundException {
        Book book = FindUtils.findBook(bookRepository, bookId);
        mark.getId().setBookId(book.getId());
        mark.setBook(book);
    }

    private void setAuthorToMark(Mark mark, String id) throws NotFoundException {
        User author = FindUtils.findUser(userRepository, id);
        mark.getId().setUserId(author.getId());
        mark.setUser(author);
    }

    @Override
    public ResponseEntity<Float> getAvgMark(Long bookId) throws NotFoundException {
        Book book = FindUtils.findBook(bookRepository, bookId);
        Float res = 0f;
        if (book.getAvgMark() != null) {
            res = book.getAvgMark().getAvgMark();
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<MarkDto> getBookMark(String userId, Long bookId) throws NotFoundException {
        User user = FindUtils.findUser(userRepository, userId);
        Optional<Mark> mark = markRepository.findById(new MarkKey(user.getId(), bookId));
        if (mark.isEmpty()) {
            throw new NotFoundException("Couldn't find mark by " + userId + " for book with id: " + bookId);
        }
        return ResponseEntity.ok(mapper.map(mark, MarkDto.class));
    }

    @Override
    public ResponseEntity<IdDto> addBookMark(MarkDto markDto) throws NotFoundException, AuthException,
            BadRequestException {
        Mark mark = mapper.map(markDto, Mark.class);
        if (!AuthServiceCommon.isSamePerson(markDto.getLogin())) {
            throw new AuthException("You cannot add mark from another user");
        }
        setBookToMark(mark, markDto.getBookId());
        setAuthorToMark(mark, markDto.getLogin());
        if (!markIsPresent(mark)) {
            markRepository.save(mark);
        } else {
            throw new BadRequestException("The mark already exist");
        }
        return ResponseEntity.ok(new IdDto().id(mark.getId().getBookId()));
    }

    @Override
    public ResponseEntity<IdDto> updateBookMark(MarkDto markDto) throws NotFoundException, AuthException {
        Mark newMark = mapper.map(markDto, Mark.class);
        if (!AuthServiceCommon.checkAuthorities(markDto.getLogin())) {
            throw new AuthException("No rights");
        }
        setBookToMark(newMark, markDto.getBookId());
        setAuthorToMark(newMark, markDto.getLogin());
        if (markIsPresent(newMark)) {
            markRepository.save(newMark);
        } else {
            throw new NotFoundException("Couldn't find previous mark");
        }
        return ResponseEntity.ok(new IdDto().id(newMark.getId().getBookId()));
    }

    @Override
    public ResponseEntity<Void> deleteBookMark(String userId, Long bookId) throws AuthException,
            NotFoundException {
        if (!checkAuthorities(userId)) {
            throw new AuthException("No rights");
        }
        User user = FindUtils.findUser(userRepository, userId);
        markRepository.deleteById(new MarkKey(user.getId(), bookId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean markIsPresent(Mark mark) {
        Optional<Mark> markOptional = markRepository.findById(mark.getId());
        return markOptional.isPresent();
    }
}

