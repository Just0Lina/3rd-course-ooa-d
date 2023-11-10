package literavibe.services;

import org.springframework.http.ResponseEntity;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.BookDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;

import java.util.List;

public interface BookService {
    ResponseEntity<BookDto> getBookById(Long id) throws NotFoundException, AuthException;

    ResponseEntity<IdDto> addBook(BookDto bookDto) throws NotFoundException, BadRequestException, AuthException;

    ResponseEntity<IdDto> updateBook(BookDto bookDto) throws NotFoundException, BadRequestException,
            AuthException;

    ResponseEntity<List<BookDto>> searchBooksByName(String name, Integer limit) throws NotFoundException, AuthException;

    ResponseEntity<Void> deleteBook(Long id) throws NotFoundException;

    ResponseEntity<List<BookDto>> filterContent(Integer limit) throws AuthException, NotFoundException;
}
