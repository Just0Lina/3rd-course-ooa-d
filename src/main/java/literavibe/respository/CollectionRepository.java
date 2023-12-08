package literavibe.respository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import literavibe.model.entities.Collection;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends CrudRepository<Collection, Long> {
    @Query(value = """
                   SELECT * FROM litera_vibe.collections
                    WHERE name ILIKE :namePart || '%'
                    UNION
                    SELECT * FROM litera_vibe.collections
                    WHERE name ILIKE '% ' || :namePart || '%'
                    ORDER BY name
                    LIMIT CASE WHEN (:limit > 0) THEN :limit END
            """, nativeQuery = true)
    List<Collection> findByNameContaining(Long limit, String namePart);

    Optional<Collection> findByName(String name);

    @Modifying()
    @Transactional
    @Query(value = """
            INSERT INTO litera_vibe.collections_distribution(collection_id, book_id)
             VALUES (:collectionId, :bookId)
            """, nativeQuery = true)
    void addBookToCollection(long bookId, long collectionId);

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM litera_vibe.collections_distribution
            WHERE  collection_id=:collectionId AND book_id =:bookId
            """, nativeQuery = true)
    void deleteBookFromCollection(Long bookId, Long collectionId);

//    List<Collection> findByAuthorId(Long id);

    @Query(value = """
          SELECT * FROM litera_vibe.collections 
          JOIN collections_distribution cd ON collections.id = cd.collection_id
          WHERE book_id =:bookId AND collection_id=:collectionId
            """, nativeQuery = true)
    Optional<Collection> findBook(Long bookId, Long collectionId);

//    @Query(value = """
//          SELECT * FROM litera_vibe.collections
//          WHERE author_id =:id AND name=:name
//            """, nativeQuery = true)
//    Optional<Collection> findByAuthorIdUserBookCollection(Long id, String name);

    @Query(value = """
          SELECT book_id FROM litera_vibe.collections_distribution 
          WHERE collection_id =:id
            """, nativeQuery = true)
    List<Long> findBookIdsInCollection(Long id);
}
