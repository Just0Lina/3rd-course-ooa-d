package literavibe.security.service;

import lombok.NonNull;
import literavibe.model.exceptions.AuthException;
import literavibe.model.exceptions.NotFoundException;
import literavibe.security.dto.JwtRequest;
import literavibe.security.dto.JwtResponse;

public interface AuthService {

    JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException, NotFoundException;

    JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException, NotFoundException;

    JwtResponse refresh(@NonNull String refreshToken) throws AuthException, NotFoundException;


}