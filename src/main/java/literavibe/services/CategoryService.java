package literavibe.services;

import literavibe.model.dto.BookDto;
import org.springframework.http.ResponseEntity;
import literavibe.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    ResponseEntity<List<CategoryDto>> getCategories();

    ResponseEntity<List<BookDto>> getBooksFromCategory(Long id, Integer limit);

    ResponseEntity<Void> deleteBooksFromCategory(Long id, Long bookId);

    ResponseEntity<Void> addCategoryToBook(Long id, Long categoryId);
}
