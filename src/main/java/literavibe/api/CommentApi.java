package literavibe.api;

import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import literavibe.config.Constants;
import literavibe.model.dto.CommentDto;
import literavibe.model.dto.IdDto;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;

import java.util.List;

@RequestMapping(Constants.BASE_API_PATH + "/comments")
@Validated
public interface CommentApi {
    @PostMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<IdDto> commentPost(@RequestBody CommentDto commentDto) throws NotFoundException;


    @PutMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<IdDto> commentUpdate(@RequestBody CommentDto commentDto) throws NotFoundException,
            BadRequestException;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<Void> commentDelete(@PathVariable("id") @Positive(message = "comment id must be positive") Long id) throws NotFoundException;

    @GetMapping("/{id}")
    ResponseEntity<List<CommentDto>> getBookComments(
            @PathVariable("id") @Positive(message = "book id must be positive") Long id);
}
