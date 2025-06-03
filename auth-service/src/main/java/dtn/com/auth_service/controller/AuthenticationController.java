package dtn.com.auth_service.controller;

import dtn.com.auth_service.dto.request.LoginRequest;
import dtn.com.auth_service.dto.request.RegisterRequest;
import dtn.com.auth_service.dto.response.AuthenticationResponse;
import dtn.com.auth_service.dto.response.RegisterResponse;
import dtn.com.auth_service.service.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        RegisterResponse registerResponse = authenticationService.register(registerRequest);
        return ResponseEntity
                .status(registerResponse.getStatus())
                .body(registerResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        AuthenticationResponse authenticationResponse = authenticationService.login(loginRequest);
        return ResponseEntity
                .status(authenticationResponse.getStatus())
                .body(authenticationResponse);
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshToken(@RequestHeader("Authorization") String authHeader) throws ValidationException {
        return authenticationService.refreshToken(authHeader);
    }

}
