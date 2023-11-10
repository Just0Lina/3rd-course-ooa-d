package literavibe.services;

import org.springframework.http.ResponseEntity;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.MarkDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;

public interface MarkService {

    ResponseEntity<Float> getAvgMark(Long bookId) throws NotFoundException;

    ResponseEntity<MarkDto> getBookMark(String userId, Long bookId) throws NotFoundException;
    ResponseEntity<IdDto> addBookMark(MarkDto mark) throws NotFoundException, AuthException, BadRequestException;

    ResponseEntity<IdDto> updateBookMark(MarkDto mark) throws NotFoundException, BadRequestException, AuthException;

    ResponseEntity<Void> deleteBookMark(String userId, Long bookId) throws AuthException, NotFoundException,
            BadRequestException;
}
