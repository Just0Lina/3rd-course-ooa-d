package literavibe.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import literavibe.config.Constants;
import literavibe.model.dto.BookDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Valid
@RequestMapping(Constants.BASE_API_PATH + "/collections")
public interface CollectionApi {
    @PostMapping(value = "/content")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<Void> collectionContentPost(@RequestParam("book_id") @PositiveOrZero Long bookId,
                                               @RequestParam("collection_id") @NotBlank Long collectionId) throws NotFoundException, AuthException;

    @DeleteMapping(value = "/content")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<Void> collectionContentDelete(@RequestParam("book_id") @PositiveOrZero Long bookId,
                                                 @RequestParam("collection_id") @NotBlank Long collectionId) throws NotFoundException, AuthException;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping(value = "/read")
    ResponseEntity<Void> bookRead(@RequestParam("book_id") @PositiveOrZero Long bookId) throws
            NotFoundException, AuthException;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping(value = "/liked")
    ResponseEntity<Void> bookLiked(@RequestParam("book_id") @PositiveOrZero Long bookId) throws
            NotFoundException, AuthException;

    @GetMapping(value = "/{id}")
    ResponseEntity<List<BookDto>> getBooksFromCollection(@PathVariable(value = "id") Long id)
            throws NotFoundException, AuthException, BadRequestException;

}
