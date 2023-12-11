package literavibe.respository;

import literavibe.model.entities.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Long> {

    Note getNoteByBookIdAndUserId(Long id, Long id1);
}
