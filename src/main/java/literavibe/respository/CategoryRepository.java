package literavibe.respository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import literavibe.model.entities.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAll();

    @Query(value = """
            SELECT book_id FROM litera_vibe.categories_distribution 
            WHERE category_id=:id
            LIMIT :limit
              """, nativeQuery = true)
    List<Long> findBooksWithCategoryId(@Param("id") Long inline, @Param("limit") Integer limit);


    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM litera_vibe.categories_distribution
            WHERE  category_id=:categoryId AND book_id =:bookId
            """, nativeQuery = true)
    void deleteByCategoryBookId(Long categoryId, Long bookId);

    @Modifying()
    @Transactional
    @Query(value = """
            INSERT INTO litera_vibe.categories_distribution(category_id, book_id) VALUES (:categoryId, :bookId);
            """, nativeQuery = true)
    void addBookToCategory(Long bookId, Long categoryId);
    
    @Query(value = """
            WITH categoriesIds AS (SELECT category_id FROM litera_vibe.categories_distribution 
            WHERE book_id=:id)
            SELECT * FROM litera_vibe.categories where id in (SELECT category_id from categoriesIds)
              """, nativeQuery = true)
    List<Category> findCategoriesWithBookId(Long id);
}
