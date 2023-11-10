package literavibe.services;

import org.springframework.http.ResponseEntity;
import literavibe.model.dto.CommentDto;
import literavibe.model.dto.IdDto;
import literavibe.model.exceptions.NotFoundException;

import java.util.List;

public interface CommentService {
    ResponseEntity<IdDto> postComment(CommentDto commentDto) throws NotFoundException;

    ResponseEntity<IdDto> updateComment(CommentDto commentDto) throws NotFoundException;

    ResponseEntity<Void> deleteComment(Long commentId) throws NotFoundException;

    ResponseEntity<List<CommentDto>> getBookComments(Long id);
}
