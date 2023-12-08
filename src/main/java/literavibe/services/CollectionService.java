package literavibe.services;

import literavibe.model.dto.BookDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CollectionService {

    ResponseEntity<Void> addBookToCollection(Long book,Long collectionId) throws NotFoundException, AuthException;

    ResponseEntity<Void> deleteBookFromCollection(Long book, Long collectionId) throws NotFoundException, AuthException;


    ResponseEntity<List<BookDto>> getCollectionBooksById(Long id) throws NotFoundException;

    ResponseEntity<Void> addLikedBook(Long bookId) throws NotFoundException, AuthException;

    ResponseEntity<Void> addReadBook(Long bookId) throws NotFoundException, AuthException;
}
