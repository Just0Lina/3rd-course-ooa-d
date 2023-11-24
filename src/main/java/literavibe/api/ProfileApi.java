package literavibe.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import literavibe.config.Constants;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.UserDto;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.model.exceptions.UserException;

import java.util.List;

@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
@RequestMapping(Constants.BASE_API_PATH + "/profile")
@Validated
public interface ProfileApi {

    @GetMapping
    ResponseEntity<UserDto> profileGet() throws Exception;

    @PutMapping
    ResponseEntity<IdDto> profilePut(@RequestBody UserDto profileDto) throws BadRequestException, NotFoundException;

    @PostMapping
    ResponseEntity<IdDto> profilePost(@RequestBody UserDto profileDto) throws BadRequestException, NotFoundException, UserException;

//    @GetMapping("/{login}")
//    ResponseEntity<List<UserProfileDto>> profileByIdGet(
//            @Size(max = 128) @NotBlank(message = "name must be not blank") @PathVariable("login") String login,
//            @RequestParam(value = "limit", required = false) @Positive(message = "limit must be positive") Integer limit) throws Exception;


}
