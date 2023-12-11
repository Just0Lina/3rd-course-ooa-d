package literavibe.services.impl;

import literavibe.model.dto.NoteDto;
import literavibe.model.dto.IdDto;
import literavibe.model.entities.Book;
import literavibe.model.entities.Note;
import literavibe.model.entities.User;
import literavibe.model.exceptions.NotFoundException;
import literavibe.respository.BookRepository;
import literavibe.respository.NoteRepository;
import literavibe.respository.UserRepository;
import literavibe.security.service.impl.AuthServiceCommon;
import literavibe.services.NoteService;
import literavibe.utils.FindUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final ModelMapper mapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(ModelMapper mapper, BookRepository bookRepository, UserRepository userRepository,
                           NoteRepository noteRepository) {
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public ResponseEntity<IdDto> postNote(NoteDto noteDto) throws NotFoundException {
        Note note = mapper.map(noteDto, Note.class);
        note.setId(null);
        User user = FindUtils.findUser(userRepository, noteDto.getLogin());
        Book book = FindUtils.findBook(bookRepository, noteDto.getBookId());
        note.setUser(user);
        note.setBook(book);

        Note savedNote = noteRepository.save(note);
        return ResponseEntity.ok(new IdDto().id(savedNote.getId()));
    }

    @Override
    public ResponseEntity<IdDto> updateNote(NoteDto noteDto) throws NotFoundException {
        Note note = FindUtils.findNote(noteRepository, noteDto.getId());
        if (AuthServiceCommon.checkAuthorities(note.getUser().getLogin())) {
            note.setContent(noteDto.getContent());
        }
        Note savedNote = noteRepository.save(note);
        return ResponseEntity.ok(new IdDto().id(savedNote.getId()));
    }

    @Override
    public ResponseEntity<Void> deleteNote(Long noteId) throws NotFoundException {
        Note note = FindUtils.findNote(noteRepository, noteId);
        if (AuthServiceCommon.checkAuthorities(note.getUser().getLogin())) {
            noteRepository.deleteById(noteId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<NoteDto> getBookNotes(Long id) throws NotFoundException {
        User user = FindUtils.findUser(userRepository, AuthServiceCommon.getUserLogin());
        Note note = noteRepository.getNoteByBookIdAndUserId(id, user.getId());
        if (note == null) throw new NotFoundException("Not found note");
        NoteDto dto = mapper.map(note, NoteDto.class);
        dto.setLogin(note.getUser().getLogin());
        return ResponseEntity.ok(dto);
    }
}
