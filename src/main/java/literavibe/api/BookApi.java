package literavibe.api;

import literavibe.model.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import literavibe.config.Constants;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.BookDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

@RequestMapping(Constants.BASE_API_PATH + "/books")
@Validated
public interface BookApi {

    @GetMapping(value = "/{id}")
    ResponseEntity<BookDto> bookIdGet(
            @PathVariable("id") @PositiveOrZero(message = "book id must be not negative") Long id) throws
            NotFoundException, AuthException;


    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping(value = "/read")
    ResponseEntity<List<BookDto>> booksRead() throws
            NotFoundException, AuthException;
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping(value = "/liked")
    ResponseEntity<List<BookDto>> booksLiked() throws
            NotFoundException, AuthException;




    @GetMapping(value = "/search/{name}")
    ResponseEntity<List<BookDto>> bookSearchNameGet(
            @Size(max = 128) @NotBlank(message = "name must be not blank") @PathVariable("name") String name,
            @RequestParam(value = "limit", required = false) @Positive(message = "limit must be positive") Integer limit) throws
            NotFoundException, AuthException;

    @GetMapping
    ResponseEntity<List<BookDto>> getBooksRecommendations(
            @RequestParam(value = "limit", required = false) @Positive(message = "limit must be positive") Integer limit) throws
            NotFoundException, AuthException;
}
