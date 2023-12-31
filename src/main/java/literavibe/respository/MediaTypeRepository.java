package literavibe.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import literavibe.model.entities.MediaType;

import java.util.Optional;

@Repository
public interface MediaTypeRepository extends CrudRepository<MediaType, Long>{
    Optional<MediaType> findByMimeType(String mimeType);
}
