package literavibe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import literavibe.api.ProfileApi;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.UserDto;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.model.exceptions.UserException;
import literavibe.security.service.UserService;

import java.util.List;

@CrossOrigin(maxAge = 1440)
@RestController
public class ProfileApiController implements ProfileApi {
    private final UserService userService;

    @Autowired
    public ProfileApiController(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ResponseEntity<UserDto> profileGet() throws Exception {
        return userService.getUserProfile();
    }

    @Override
    public ResponseEntity<IdDto> profilePut(UserDto profileDto) throws BadRequestException, NotFoundException {
        return userService.profileUpdate(profileDto);
    }
    @Override
    public ResponseEntity<IdDto> profilePost(UserDto profileDto) throws BadRequestException, NotFoundException, UserException {
        return userService.profilePost(profileDto);
    }

//    @Override
//    public ResponseEntity<List<UserProfileDto>> profileByIdGet(String login, Integer limit) throws Exception {
//        return userService.getUserProfile(login, limit);
//    }
}
