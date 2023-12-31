package literavibe.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import literavibe.config.Constants;
import literavibe.model.dto.IdDto;
import literavibe.model.exceptions.InvalidMediaTypeException;
import literavibe.model.exceptions.NotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Valid
@RequestMapping(Constants.BASE_API_PATH + "/media")
public interface MediaApi {
    @GetMapping(value = "/{id}")
    ResponseEntity<byte[]> mediaGet(@PathVariable("id") @PositiveOrZero Long id) throws NotFoundException;


    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping
    ResponseEntity<IdDto> mediaPost(@RequestHeader(HttpHeaders.CONTENT_TYPE) String contentTypeHeader,
                                    @RequestBody @NotNull byte[] data) throws InvalidMediaTypeException;

}
