package literavibe.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import literavibe.config.Constants;
import literavibe.model.dto.UserDto;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.BadRequestException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.model.exceptions.UserException;
import literavibe.security.dto.JwtRequest;
import literavibe.security.dto.JwtResponse;
import literavibe.security.dto.RefreshJwtRequest;
import literavibe.security.service.impl.AuthServiceImplMobile;
import literavibe.security.service.impl.AuthServiceImplWeb;

@RestController
@RequestMapping(Constants.BASE_API_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImplMobile authServiceMobile;
    private final AuthServiceImplWeb authServiceWeb;


    @PostMapping("/registration/mobile")
    public ResponseEntity<JwtResponse> registrationMobile(@RequestBody UserDto user) throws AuthException,
            NotFoundException, UserException, BadRequestException {
        final JwtResponse token = authServiceMobile.registration(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/registration/web")
    public ResponseEntity<JwtResponse> registrationWeb(@RequestBody UserDto user) throws AuthException,
            NotFoundException, UserException, BadRequestException {
        final JwtResponse token = authServiceWeb.registration(user);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/login/mobile")
    public ResponseEntity<JwtResponse> loginMobile(@RequestBody JwtRequest authRequest) throws AuthException,
            NotFoundException {
        final JwtResponse token = authServiceMobile.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login/web")
    public ResponseEntity<JwtResponse> loginWeb(@RequestBody JwtRequest authRequest) throws AuthException,
            NotFoundException {
        final JwtResponse token = authServiceWeb.login(authRequest);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/auth/refresh/web")
    public ResponseEntity<JwtResponse> getNewRefreshTokenWeb(
            @CookieValue(value = "refreshToken") String refreshToken) throws AuthException, NotFoundException {
        final JwtResponse token = authServiceWeb.refresh(refreshToken);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/refresh/mobile")
    public ResponseEntity<JwtResponse> getNewRefreshMobile(@RequestBody RefreshJwtRequest request) throws AuthException,
            NotFoundException {
        final JwtResponse token = authServiceMobile.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }


    @PostMapping("/auth/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException,
            NotFoundException {
        final JwtResponse token = authServiceMobile.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }


    @PutMapping("/profile/password/mobile")
    public ResponseEntity<JwtResponse> userUpdateMobile(@RequestBody UserDto userDto) throws NotFoundException,
            AuthException, BadRequestException {
        final JwtResponse token = authServiceMobile.changePassword(userDto);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/profile/password/web")
    public ResponseEntity<JwtResponse> userUpdateWeb(@RequestBody UserDto userDto) throws NotFoundException,
            AuthException, BadRequestException {
        final JwtResponse token = authServiceWeb.changePassword(userDto);
        return ResponseEntity.ok(token);
    }


}