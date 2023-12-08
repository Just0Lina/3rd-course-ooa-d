package literavibe.services.impl;

import literavibe.config.Constants;
import literavibe.model.dto.BookDto;
import literavibe.model.dto.CategoryDto;
import literavibe.model.dto.DateToYearConverter;
import literavibe.model.entities.Book;
import literavibe.model.entities.Category;
import literavibe.respository.BookRepository;
import literavibe.respository.CategoryRepository;
import literavibe.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               BookRepository bookRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(
                element -> modelMapper.map(element, CategoryDto.class)).toList();
        return ResponseEntity.ok(categoryDtos);
    }

    @Override
    public ResponseEntity<List<BookDto>> getBooksFromCategory(Long id, Integer limit) { //todo проверить на пустой категории
        if (limit == null) limit = Constants.MAX_RECIPES_PER_PAGE;
        List<Long> bookIds = categoryRepository.findBooksWithCategoryId(id, limit);
        List<Book> books = bookRepository.findByIds(bookIds);
        modelMapper.addConverter(new DateToYearConverter());
        List<BookDto> bookDtos = books.stream().map(
                element -> modelMapper.map(element, BookDto.class)).toList();
        return ResponseEntity.ok(bookDtos);
    }

    @Override
    public ResponseEntity<Void> deleteBooksFromCategory(Long id, Long bookId) {
        categoryRepository.deleteByCategoryBookId(id, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addCategoryToBook(Long id, Long categoryId) {
        categoryRepository.addBookToCategory(id, categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getBookCategories(Long id) {
        List<Category> categories = categoryRepository.findCategoriesWithBookId(id);

        modelMapper.addConverter(new DateToYearConverter());
        List<CategoryDto> categoryDtos = categories.stream().map(
                element -> modelMapper.map(element, CategoryDto.class)).toList();
        return ResponseEntity.ok(categoryDtos);
    }
}
