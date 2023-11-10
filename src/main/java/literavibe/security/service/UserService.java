package literavibe.security.service;

import org.springframework.http.ResponseEntity;
import literavibe.model.dto.IdDto;
import literavibe.model.dto.UserDto;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.model.exceptions.UserException;

import java.util.List;

public interface UserService {
    ResponseEntity<IdDto> postUser(UserDto userDto) throws NotFoundException, BadRequestException, UserException;

//    ResponseEntity<UserProfileDto> getUserProfile() throws Exception;
//    ResponseEntity<List<UserProfileDto>>getUserProfile(String login, Integer limit) throws Exception;


//    ResponseEntity<IdDto> profileUpdate(UserProfileDto profileDto) throws BadRequestException, NotFoundException;
//    ResponseEntity<IdDto> profilePost(UserProfileDto profileDto) throws BadRequestException, NotFoundException, UserException;
}