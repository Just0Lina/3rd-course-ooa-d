package literavibe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import literavibe.api.MediaApi;
import literavibe.model.dto.IdDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import literavibe.model.exceptions.InvalidMediaTypeException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.services.MediaService;

@RestController
@CrossOrigin(maxAge = 1440)
public class MediaApiController implements MediaApi {

    private final MediaService service;

    @Autowired
    public MediaApiController(MediaService service){
        this.service = service;
    }


    @Override
    public ResponseEntity<byte[]> mediaGet(Long id) throws NotFoundException {
        return service.getMediaById(id);
    }


    @Override
    public ResponseEntity<IdDto> mediaPost(String contentTypeHeader, byte[] data) throws InvalidMediaTypeException {
        return service.addMedia(contentTypeHeader,data);
    }


}
