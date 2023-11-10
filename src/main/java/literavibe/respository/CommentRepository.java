package literavibe.respository;

import org.springframework.data.repository.CrudRepository;
import literavibe.model.entities.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> getCommentsByBookId(Long id);

}
