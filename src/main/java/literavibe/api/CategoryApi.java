package literavibe.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import literavibe.config.Constants;
import literavibe.model.dto.CategoryDto;
import literavibe.model.dto.BookDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;

import java.util.List;

@Valid
@RequestMapping(Constants.BASE_API_PATH + "/categories")
public interface CategoryApi {
    @GetMapping
    ResponseEntity<List<CategoryDto>> categoriesGet() throws NotFoundException;

    @GetMapping(value = "/book/{id}")
    ResponseEntity<List<CategoryDto>> bookCategoriesGet(
            @PathVariable("id") @PositiveOrZero(message = "book id must be not negative") Long id) throws
            NotFoundException, AuthException;

    @GetMapping(value = "/{id}")
    ResponseEntity<List<BookDto>> categoriesGetById(@PathVariable(value = "id") Long id,
                                                      @RequestParam(value = "limit", required = false) @PositiveOrZero Integer limit)
            throws NotFoundException, AuthException, BadRequestException;

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> categoriesDeleteByCategoryAndBookId(@PathVariable(value = "id") Long id,
                                                               @RequestParam(value = "book_id", required = true) @PositiveOrZero Long bookId) throws NotFoundException;

    @PostMapping(value = "/books/{id}")
    ResponseEntity<Void> addCategoryToBook(@PathVariable(value = "id") Long id, @RequestParam(value = "category_id", required = true) Long categoryId) throws NotFoundException, AuthException, BadRequestException;

}
