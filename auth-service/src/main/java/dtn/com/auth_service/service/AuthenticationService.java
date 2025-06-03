package dtn.com.auth_service.service;

import dtn.com.auth_service.dto.request.LoginRequest;
import dtn.com.auth_service.dto.request.RegisterRequest;
import dtn.com.auth_service.dto.response.AuthenticationResponse;
import dtn.com.auth_service.dto.response.RegisterResponse;
import dtn.com.auth_service.model.Role;
import dtn.com.auth_service.model.User;
import dtn.com.auth_service.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;


@Slf4j
@Service
public class AuthenticationService {
    private static final int TOKEN_INDEX = 7;

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenTicationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenTicationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenTicationManager = authenTicationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String userName = registerRequest.getUsername();

        Optional<User> userFoundByEmail = userRepository.findByEmail(email);
        Optional<User> userFoundByUsername = userRepository.findByUsername(userName);

        if (userFoundByEmail.isPresent() || userFoundByUsername.isPresent()) {
            throw new ConstraintViolationException("User already exists!", null);
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.toEnum(registerRequest.getRole()))
                .build();

        userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("User created");

        return response;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        authenTicationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        //Generate access token and refresh token
        Optional<User> userFoundByUsername = userRepository.findByUsername(loginRequest.getUsername());
        if (userFoundByUsername.isPresent()) {
            User user = userFoundByUsername.get();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            AuthenticationResponse response = new AuthenticationResponse();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setUserId(user.getId());
            response.setMessage("Login successfully");
            response.setStatus(HttpStatus.OK.value());
            return response;
        } else {
            AuthenticationResponse response = new AuthenticationResponse();
            response.setMessage("Login failed");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return response;
        }
    }

    public AuthenticationResponse refreshToken(String authHeader) throws ValidationException {
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            AuthenticationResponse response = new AuthenticationResponse();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Unauthorized!");
            return response;
        }

        String refreshToken = authHeader.substring(TOKEN_INDEX);

        //Get userName from refreshToken
        String userName = jwtService.extractUsername(refreshToken);
        if (!StringUtils.hasText(userName)) {
            throw new ValidationException("Username is empty");
        }

        //Get User's data from database
        Optional<User> userFoundByUsername = userRepository.findByUsername(userName);
        if (userFoundByUsername.isEmpty()) {
            throw new UsernameNotFoundException(userName);
        }

        User user = userFoundByUsername.get();
        if (!StringUtils.hasText(user.getAccessToken()) || !StringUtils.hasText(user.getRefreshToken())) {
            throw new ValidationException("Username is empty");
        }

        //Generate access token and refresh token
        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        user.setAccessToken(accessToken);
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        //Response access token and refresh token to client
        AuthenticationResponse response = new AuthenticationResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setUserId(user.getId());
        response.setMessage("Refresh token successfully");
        response.setStatus(HttpStatus.OK.value());
        return response;
    }
}
