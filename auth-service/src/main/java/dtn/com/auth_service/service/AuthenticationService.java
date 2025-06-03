package dtn.com.auth_service.service;

//import dtn.com.auth_service.dto.request.LoginRequest;
import dtn.com.auth_service.dto.request.RegisterRequest;
//import dtn.com.auth_service.dto.response.AuthenticationResponse;
import dtn.com.auth_service.dto.response.RegisterResponse;
//import dtn.com.auth_service.exception.ValidationException;
//import dtn.com.auth_service.model.Role;

//
import dtn.com.auth_service.model.Role;
import dtn.com.auth_service.model.User;
import dtn.com.auth_service.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
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
@RequiredArgsConstructor
public class AuthenticationService {
    private static final int TOKEN_INDEX = 7;

    private final UserRepository userRepository;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenTicationManager;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest registerRequest) {
        //Check email, username is existed in db
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

        return RegisterResponse.builder()
                .status(HttpStatus.OK.value())
                .message("User created")
                .build();
    }
}