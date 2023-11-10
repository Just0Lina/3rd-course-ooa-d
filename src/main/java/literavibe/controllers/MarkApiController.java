package literavibe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import literavibe.api.MarkApi;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.MarkDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.services.MarkService;

@CrossOrigin(maxAge = 1440)
@RestController
public class MarkApiController implements MarkApi {
    private final MarkService markService;

    @Autowired
    public MarkApiController(MarkService markService) {
        this.markService = markService;
    }

    @Override
    public ResponseEntity<Float> getAvgMark(Long id) throws NotFoundException {
        return markService.getAvgMark(id);
    }

    @Override
    public ResponseEntity<MarkDto> getMark(String userId, Long bookId) throws NotFoundException {
        return markService.getBookMark(userId, bookId);
    }

    @Override
    public ResponseEntity<IdDto> markPost(MarkDto mark) throws BadRequestException, NotFoundException, AuthException {
        return markService.addBookMark(mark);
    }

    @Override
    public ResponseEntity<IdDto> markUpdate(MarkDto mark) throws BadRequestException, NotFoundException, AuthException {
        return markService.updateBookMark(mark);
    }

    @Override
    public ResponseEntity<Void> markDelete(String userId, Long bookId) throws NotFoundException, AuthException,
            BadRequestException {
        return markService.deleteBookMark(userId, bookId);
    }


}
