package literavibe.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import literavibe.model.entities.Media;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long> {
}
