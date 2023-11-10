package literavibe.respository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import literavibe.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query(value = """
                    SELECT * FROM litera_vibe.users
                    WHERE id ILIKE :namePart || '%'
                    UNION
                    SELECT * FROM litera_vibe.users
                    WHERE id ILIKE '% ' || :namePart || '%'
                    ORDER BY id
                    LIMIT CASE WHEN (:limit > 0) THEN :limit END
            """, nativeQuery = true)
    List<User> findByLoginContaining(@Param("namePart") String inline, @Param("limit") Integer limit);

}
