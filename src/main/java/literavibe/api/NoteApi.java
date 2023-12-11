package literavibe.api;

import jakarta.validation.constraints.Positive;
import literavibe.config.Constants;
import literavibe.model.dto.NoteDto;
import literavibe.model.dto.IdDto;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Constants.BASE_API_PATH + "/notes")
@Validated
public interface NoteApi {
    @PostMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<IdDto> notePost(@RequestBody NoteDto noteDto) throws NotFoundException;


    @PutMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<IdDto> noteUpdate(@RequestBody NoteDto noteDto) throws NotFoundException,
            BadRequestException;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<Void> noteDelete(@PathVariable("id") @Positive(message = "note id must be positive") Long id) throws NotFoundException;

    @GetMapping("/{id}")
    ResponseEntity<NoteDto> getBookNotes(
            @PathVariable("id") @Positive(message = "book id must be positive") Long id) throws NotFoundException;
}
