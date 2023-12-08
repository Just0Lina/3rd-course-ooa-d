package literavibe.services.impl;

import literavibe.model.dto.BookDto;

import literavibe.model.dto.IdDto;
import literavibe.model.entities.Book;
import literavibe.model.entities.Collection;
import literavibe.model.entities.Media;
import literavibe.model.entities.User;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.respository.BookRepository;
import literavibe.respository.CollectionRepository;
import literavibe.respository.MediaRepository;
import literavibe.respository.UserRepository;
import literavibe.security.service.impl.AuthServiceCommon;
import literavibe.services.CollectionService;
import literavibe.utils.FindUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;


    private final ModelMapper mapper;


    @Autowired
    public CollectionServiceImpl(CollectionRepository repository, BookRepository bookRepository,
                                 UserRepository userRepository, MediaRepository mediaRepository, ModelMapper mapper) {
        this.collectionRepository = repository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ResponseEntity<Void> addBookToCollection(Long bookId, Long collectionId) throws NotFoundException,
            AuthException {
        User user = FindUtils.findUser(userRepository, AuthServiceCommon.getUserLogin());
        Collection collection = FindUtils.findCollection(collectionRepository, collectionId);
        if (!user.equals(collection.getAuthor())) {
            throw new AuthException("No rights");
        }
        Book book = FindUtils.findBook(bookRepository, bookId);
        collectionRepository.addBookToCollection(book.getId(), collection.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteBookFromCollection(Long bookId, Long collectionId) throws NotFoundException,
            AuthException {
        User user = FindUtils.findUser(userRepository, AuthServiceCommon.getUserLogin());
        Collection collection = FindUtils.findCollection(collectionRepository, collectionId);
        if (collection.getAuthor() == null || !user.equals(collection.getAuthor())) {
            throw new AuthException("No rights");
        }
        Book book = FindUtils.findBook(bookRepository, bookId);
        collectionRepository.deleteBookFromCollection(book.getId(), collection.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BookDto>> getCollectionBooksById(Long id) throws NotFoundException {
        List<Long> bookIds = collectionRepository.findBookIdsInCollection(id);
        List<Book> books = bookRepository.findByIds(bookIds);
        List<BookDto> bookDtos = books.stream().map(
                element -> mapper.map(element, BookDto.class)).toList();
        return ResponseEntity.ok(bookDtos);
    }

    @Override
    public ResponseEntity<Void> addLikedBook(Long bookId) throws NotFoundException, AuthException {
        User user = FindUtils.findUser(userRepository, AuthServiceCommon.getUserLogin());
        Optional<Collection> collectionTmp = collectionRepository.findByName("Liked_" + user.getLogin());
        Collection collection;
        if (collectionTmp.isEmpty()) {
            collection = new Collection("Liked_" + user.getLogin(), 0, user);
            collectionRepository.save(collection);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            collection = collectionTmp.get();
        }
        addBookToCollection(bookId, collection.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addReadBook(Long bookId) throws NotFoundException, AuthException {
        User user = FindUtils.findUser(userRepository, AuthServiceCommon.getUserLogin());
        Optional<Collection> collectionTmp = collectionRepository.findByName("Read_" + user.getLogin());
        Collection collection;
        if (collectionTmp.isEmpty()) {
            collection = new Collection("Read_" + user.getLogin(), 0, user);
            collectionRepository.save(collection);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            collection = collectionTmp.get();
        }
        addBookToCollection(bookId, collection.getId());
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
