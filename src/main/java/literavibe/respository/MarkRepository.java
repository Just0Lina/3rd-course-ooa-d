package literavibe.respository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import literavibe.model.entities.Mark;
import literavibe.model.entities.MarkKey;

import java.util.Optional;

@Repository
public interface MarkRepository extends CrudRepository<Mark, MarkKey>{
    @Query(value = """
                    SELECT * FROM litera_vibe.marks m WHERE m.user_id = :userId AND m.book_id = :bookId
            """, nativeQuery = true)
    Optional<Mark> findByUserIdAndBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query(value = """
                    DELETE FROM litera_vibe.marks m WHERE m.user_id = :userId AND m.book_id = :bookId
            """, nativeQuery = true)
    void deleteByIdUserIdAndIdBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);

}
