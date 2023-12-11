package literavibe.controllers;

import literavibe.api.NoteApi;
import literavibe.model.dto.NoteDto;
import literavibe.model.dto.IdDto;
import literavibe.model.exceptions.NotFoundException;
import literavibe.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 1440)
@RestController
public class NoteApiController implements NoteApi {

    private final NoteService noteService;

    @Autowired
    public NoteApiController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public ResponseEntity<IdDto> notePost(NoteDto noteDto) throws NotFoundException {
        return noteService.postNote(noteDto);
    }

    @Override
    public ResponseEntity<IdDto> noteUpdate(NoteDto noteDto) throws NotFoundException {
        return noteService.updateNote(noteDto);
    }

    @Override
    public ResponseEntity<Void> noteDelete(Long id) throws NotFoundException {
        return noteService.deleteNote(id);
    }

    @Override
    public ResponseEntity<NoteDto> getBookNotes(Long id) throws NotFoundException {
        return noteService.getBookNotes(id);
    }
}
