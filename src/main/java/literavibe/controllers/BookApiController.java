package literavibe.controllers;

import literavibe.model.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import literavibe.api.BookApi;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.BookDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.services.BookService;

import java.util.List;

//TODO категории в рецепты добавить

@CrossOrigin(maxAge = 1440)
@RestController
public class BookApiController implements BookApi {
    private final BookService bookService;

    @Autowired
    public BookApiController(BookService bookService) {
        this.bookService = bookService;
    }


    @Override
    public ResponseEntity<BookDto> bookIdGet(Long id) throws NotFoundException, AuthException {
        return bookService.getBookById(id);
    }

    @Override
    public ResponseEntity<List<BookDto>> booksRead() throws NotFoundException, AuthException {
        return bookService.getReadBook();
    }

    @Override
    public ResponseEntity<List<BookDto>> booksLiked() throws NotFoundException, AuthException {
        return bookService.getLikedBook();
    }


    public ResponseEntity<List<BookDto>> bookSearchNameGet(String name, Integer limit) throws NotFoundException, AuthException {
        return bookService.searchBooksByName(name, limit);
    }

    @Override
    public ResponseEntity<List<BookDto>> getBooksRecommendations(Integer limit) throws NotFoundException, AuthException {
        return bookService.filterContent(limit);
    }


}
