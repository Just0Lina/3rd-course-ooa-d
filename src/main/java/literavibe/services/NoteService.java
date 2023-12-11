package literavibe.services;

import literavibe.model.dto.NoteDto;
import literavibe.model.dto.IdDto;
import literavibe.model.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface NoteService {
    ResponseEntity<IdDto> postNote(NoteDto noteDto) throws NotFoundException;

    ResponseEntity<IdDto> updateNote(NoteDto noteDto) throws NotFoundException;

    ResponseEntity<Void> deleteNote(Long noteId) throws NotFoundException;

    ResponseEntity<NoteDto> getBookNotes(Long id) throws NotFoundException;
}
