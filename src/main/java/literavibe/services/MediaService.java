package literavibe.services;

import org.springframework.http.ResponseEntity;
import literavibe.model.dto.IdDto;
import literavibe.model.exceptions.InvalidMediaTypeException;
import literavibe.model.exceptions.NotFoundException;

public interface MediaService {
    ResponseEntity<byte[]> getMediaById(Long id) throws NotFoundException;

    ResponseEntity<IdDto> addMedia(String contentTypeHeader, byte[] data) throws InvalidMediaTypeException;
}
