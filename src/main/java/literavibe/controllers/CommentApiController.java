package literavibe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import literavibe.api.CommentApi;
import literavibe.model.dto.CommentDto;
import literavibe.model.dto.IdDto;
import literavibe.model.exceptions.NotFoundException;
import literavibe.services.CommentService;

import java.util.List;

@CrossOrigin(maxAge = 1440)
@RestController
public class CommentApiController implements CommentApi {

    private final CommentService commentService;

    @Autowired
    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<IdDto> commentPost(CommentDto commentDto) throws NotFoundException {
        return commentService.postComment(commentDto);
    }

    @Override
    public ResponseEntity<IdDto> commentUpdate(CommentDto commentDto) throws NotFoundException {
        return commentService.updateComment(commentDto);
    }

    @Override
    public ResponseEntity<Void> commentDelete(Long id) throws NotFoundException {
        return commentService.deleteComment(id);
    }

    @Override
    public ResponseEntity<List<CommentDto>> getBookComments(Long id) {
        return commentService.getBookComments(id);
    }
}
