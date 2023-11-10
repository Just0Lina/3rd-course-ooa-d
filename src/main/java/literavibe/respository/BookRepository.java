package literavibe.respository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import literavibe.model.entities.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query(value = """
                    SELECT * FROM litera_vibe.books
                    WHERE name ILIKE :namePart || '%'
                    UNION
                    SELECT * FROM litera_vibe.books
                    WHERE name ILIKE '% ' || :namePart || '%'
                    ORDER BY name
                    LIMIT CASE WHEN (:limit > 0) THEN :limit END
            """, nativeQuery = true)
    List<Book> findByNameContaining(@Param("namePart") String inline, @Param("limit") Integer limit);

    // https://medium.com/swlh/sql-pagination-you-are-probably-doing-it-wrong-d0f2719cc166 - performance issue
    // вообще хотелось бы этот метод в репозиторий коллекций добавить, но там проблема с конвертацией.
    @Query(value = """
                    SELECT * FROM litera_vibe.books
                    WHERE id IN
                    (
                        SELECT book_id FROM litera_vibe.collections_distribution
                        WHERE collection_id=:collectionId
                        ORDER BY book_id
                        LIMIT :numBooks OFFSET :offset
            )
            """, nativeQuery = true)
    List<Book> findBooksWithOffsetFromCollectionById(int numBooks, int offset, long collectionId);


    Optional<Book> findBookByMediaId(Long mediaId);


    @Query(value = """
                    WITH avg_limit AS (
                        SELECT book_id FROM litera_vibe.avg_marks
                        ORDER BY avg_mark DESC
                    )
                    SELECT * FROM litera_vibe.books
                    WHERE id not in (SELECT * from avg_limit)
                    ORDER BY random()
                    LIMIT :limit
            """, nativeQuery = true)
    List<Book>  findRandomWithLimit(int limit);


    @Query(value = """
                   SELECT litera_vibe.books.*
                   FROM litera_vibe.books
                   JOIN litera_vibe.avg_marks ON books.id = litera_vibe.avg_marks.book_id
                   ORDER BY avg_mark DESC
                   LIMIT :limit
            """, nativeQuery = true)
    List<Book> findTopBooksWithLimit(Integer limit);

    @Query(value = """
                   SELECT *
                   FROM litera_vibe.books
                   WHERE id in :ids
            """, nativeQuery = true)
    List<Book> findByIds(@Param("ids") List<Long> booksIds);
}
