package literavibe.controllers;

import literavibe.api.CollectionApi;
import literavibe.model.dto.BookDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 1440)
public class CollectionApiController implements CollectionApi {

    private final CollectionService service;

    @Autowired
    public CollectionApiController(CollectionService service) {
        this.service = service;
    }


    @Override
    public ResponseEntity<Void> collectionContentDelete(Long book, Long collectionId) throws NotFoundException, AuthException {
        return service.deleteBookFromCollection(book, collectionId);
    }

    @Override
    public ResponseEntity<Void> bookRead(Long bookId) throws NotFoundException, AuthException {
        return service.addReadBook(bookId);
    }

    @Override
    public ResponseEntity<Void> bookLiked(Long bookId) throws NotFoundException, AuthException {
        return service.addLikedBook(bookId);

    }

    @Override
    public ResponseEntity<Void> deleteBookRead(Long bookId) throws NotFoundException, AuthException {
        return service.deleteBookRead(bookId);
    }

    @Override
    public ResponseEntity<Void> deleteBookLiked(Long bookId) throws NotFoundException, AuthException {
        return service.deleteBookLiked(bookId);
    }


    @Override
    public ResponseEntity<Void> collectionContentPost(Long book, Long collectionId) throws NotFoundException, AuthException {
        return service.addBookToCollection(book, collectionId);
    }


    @Override
    public ResponseEntity<List<BookDto>> getBooksFromCollection(Long id) throws NotFoundException, AuthException, BadRequestException {
        return service.getCollectionBooksById(id);
    }

}
