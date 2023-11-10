package literavibe.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import literavibe.model.dto.CommentDto;
import literavibe.model.dto.IdDto;
import literavibe.model.entities.Comment;
import literavibe.model.entities.Book;
import literavibe.model.entities.User;
import literavibe.model.exceptions.NotFoundException;
import literavibe.respository.CommentRepository;
import literavibe.respository.BookRepository;
import literavibe.respository.UserRepository;
import literavibe.security.service.impl.AuthServiceCommon;
import literavibe.services.CommentService;
import literavibe.utils.FindUtils;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final ModelMapper mapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper mapper, BookRepository bookRepository, UserRepository userRepository,
                              CommentRepository commentRepository) {
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity<IdDto> postComment(CommentDto commentDto) throws NotFoundException {
        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setId(null);
        User user = FindUtils.findUser(userRepository, commentDto.getLogin());
        Book book = FindUtils.findBook(bookRepository, commentDto.getBookId());
        comment.setUser(user);
        comment.setBook(book);

        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(new IdDto().id(savedComment.getId()));
    }

    @Override
    public ResponseEntity<IdDto> updateComment(CommentDto commentDto) throws NotFoundException {
        Comment comment = FindUtils.findComment(commentRepository, commentDto.getId());
        if (AuthServiceCommon.checkAuthorities(comment.getUser().getLogin())) {
            comment.setContent(commentDto.getContent());
        }
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(new IdDto().id(savedComment.getId()));
    }

    @Override
    public ResponseEntity<Void> deleteComment(Long commentId) throws NotFoundException {
        Comment comment = FindUtils.findComment(commentRepository, commentId);
        if (AuthServiceCommon.checkAuthorities(comment.getUser().getLogin())) {
            commentRepository.deleteById(commentId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentDto>> getBookComments(Long id) {
        List<Comment> comments = commentRepository.getCommentsByBookId(id);
        List<CommentDto> dtos = comments.stream().map((comment) -> mapper.map(comment, CommentDto.class)).toList();
        return ResponseEntity.ok(dtos);
    }
}
