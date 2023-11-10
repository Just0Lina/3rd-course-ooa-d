package literavibe.controllers;

import literavibe.api.CategoryApi;
import literavibe.model.dto.BookDto;
import literavibe.model.dto.CategoryDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(maxAge = 1440)
public class CategoryApiController implements CategoryApi {
    private final CategoryService service;

    @Autowired
    public CategoryApiController(CategoryService service) {
        this.service = service;
    }


    @Override
    public ResponseEntity<List<CategoryDto>> categoriesGet() throws NotFoundException {
        return service.getCategories();
    }

    @Override
    public ResponseEntity<List<BookDto>> categoriesGetById(Long id, Integer limit) throws NotFoundException, AuthException, BadRequestException {
       return service.getBooksFromCategory(id, limit);
    }

    @Override
    public ResponseEntity<Void> categoriesDeleteByCategoryAndBookId(Long id, Long bookId) throws NotFoundException {
        return service.deleteBooksFromCategory(id, bookId);
    }

    @Override
    public ResponseEntity<Void> addCategoryToBook(Long id, Long categoryId) throws NotFoundException, AuthException, BadRequestException {
        return service.addCategoryToBook(id, categoryId);
    }
}
