package literavibe.services.impl;

import literavibe.model.dto.*;
import literavibe.model.entities.Collection;
import literavibe.security.service.impl.AuthServiceCommon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import literavibe.model.entities.*;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.recommend.SlopeOne;
import literavibe.respository.*;
import literavibe.services.BookService;
import literavibe.utils.FindUtils;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    private final ModelMapper mapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final MarkRepository markRepository;
    private final MediaRepository mediaRepository;
    private final CollectionRepository collectionRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper,
                           AvgMarkRepository avgMarkRepository,
                           MarkRepository markRepository, UserRepository userRepository,
                           MediaRepository mediaRepository,
                           CollectionRepository collectionRepository) {

        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.markRepository = markRepository;
        this.mediaRepository = mediaRepository;
        this.collectionRepository = collectionRepository;
    }


    @Override
    public ResponseEntity<BookDto> getBookById(Long id) throws NotFoundException {
        Book book = FindUtils.findBook(bookRepository, id);
        mapper.addConverter(new DateToYearConverter());
        BookDto bookDto = mapper.map(book, BookDto.class);
        bookDto.setAuthors(AuthorMapper.convertAuthorsToDto(book.getAuthors())); // Assume you have a method to convert Author entity to AuthorDto
        return ResponseEntity.ok(bookDto);
    }

    private void checkMediaUniqueness(Book book) throws BadRequestException {
        Set<Long> mediaIds = new HashSet<>();
        mediaIds.add(book.getMedia().getId());

    }


    private List<Book> findBooksByName(String name, int limit) throws NotFoundException {
        List<Book> books = bookRepository.findByNameContaining(name, limit);
        if (books.isEmpty()) {
            throw new NotFoundException("Couldn't find books with substring: " + name);
        }
        return books;
    }

    @Override
    public ResponseEntity<List<BookDto>> searchBooksByName(String name, Integer limit) throws NotFoundException {
        if (limit == null) {
            limit = 0;
        }
        List<Book> books = findBooksByName(name, limit);
        List<BookDto> bookDtos = books.stream().map(book -> mapper.map(book, BookDto.class)).toList();
        return ResponseEntity.ok(bookDtos);
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long bookId) throws NotFoundException {
        Book book = FindUtils.findBook(bookRepository, bookId);
        bookRepository.deleteById(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdDto> updateBook(BookDto bookDto) throws NotFoundException, BadRequestException,
            AuthException {
        Book newBook = mapper.map(bookDto, Book.class);
        newBook.setId(bookDto.getId());
        checkMediaUniqueness(newBook);
        bookRepository.save(newBook);
        return ResponseEntity.ok(new IdDto().id(newBook.getId()));
    }

    @Override
    public ResponseEntity<IdDto> addBook(BookDto bookDto) throws NotFoundException, BadRequestException,
            AuthException {
        if (bookDto.getMediaId() == null) {
            throw new BadRequestException("Media id must be present");
        }
        Book book = mapper.map(bookDto, Book.class);
        book.setId(null);
        checkMediaUniqueness(book);
        if (mediaRepository.findById(book.getMedia().getId()).isEmpty()) {
            throw new NotFoundException("Couldn't find media with id: " + book.getMedia().getId());
        }
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.ok(new IdDto().id(savedBook.getId()));

    }


    @Override
    public ResponseEntity<List<BookDto>> filterContent(Integer limit) throws NotFoundException {
        SlopeOne recommendAlgSlopeOne = new SlopeOne(mapper, userRepository, markRepository, bookRepository);
        return ResponseEntity.ok(recommendAlgSlopeOne.recommendAlgSlopeOne(limit));
    }

    @Override
    public ResponseEntity<List<BookDto>> getLikedBook() throws NotFoundException {
        User user = FindUtils.findUser(userRepository, AuthServiceCommon.getUserLogin());
        Collection collection = collectionRepository.findByName("Liked_" + user.getLogin()).orElseThrow(() -> new NotFoundException("No such collection"));
        return getListResponseEntity(collection);
    }

    private ResponseEntity<List<BookDto>> getListResponseEntity(Collection collection) {
        List<Long> bookIds = collectionRepository.findBookIdsInCollection(collection.getId());
        List<Book> books = bookRepository.findByIds(bookIds);
        List<BookDto> bookDtos = books.stream().map(
                element -> mapper.map(element, BookDto.class)).toList();
        return ResponseEntity.ok(bookDtos);
    }


    @Override
    public ResponseEntity<List<BookDto>> getReadBook() throws NotFoundException {
        User user = FindUtils.findUser(userRepository, AuthServiceCommon.getUserLogin());
        Collection collection = collectionRepository.findByName("Read_" + user.getLogin()).orElseThrow(() -> new NotFoundException("No such collection"));
        return getListResponseEntity(collection);
    }
}
