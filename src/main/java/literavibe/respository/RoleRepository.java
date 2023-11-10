package literavibe.respository;

import org.springframework.data.repository.CrudRepository;
import literavibe.model.entities.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
