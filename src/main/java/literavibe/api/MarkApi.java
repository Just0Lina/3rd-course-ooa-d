package literavibe.api;

import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import literavibe.config.Constants;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.MarkDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;

@RequestMapping(Constants.BASE_API_PATH + "/marks")
@Validated
public interface MarkApi {

    @GetMapping("/{id}")
    ResponseEntity<Float> getAvgMark(@PathVariable("id") @Positive Long id) throws NotFoundException;

    @GetMapping
    ResponseEntity<MarkDto> getMark(@RequestParam("login") String userId,
                                    @RequestParam("book_id") Long bookId) throws NotFoundException;

    @PostMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<IdDto> markPost(@RequestBody MarkDto mark) throws BadRequestException, NotFoundException,
            AuthException;

    @PutMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<IdDto> markUpdate(@RequestBody MarkDto mark) throws BadRequestException, NotFoundException,
            AuthException;

    @DeleteMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<Void> markDelete(@RequestParam("login") String userId,
                                    @RequestParam("book_id") Long bookId) throws NotFoundException, AuthException,
            BadRequestException;
}
