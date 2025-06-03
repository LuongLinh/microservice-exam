package dtn.com.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    @NotEmpty(message = "Username must not be empty")
    private String username;

    private String firstName;

    private String lastName;

    @Email(message = "Malformed email")
    @NotNull(message = "Email must not be null")
    private String email;

    @NotNull(message = "Password must not be null")
    private String password;

    @NotNull(message = "Role can not be null")
    @Pattern(regexp = "ADMIN|MANAGER|USER", message = "The role must be ADMIN, MANAGER or USER")
    private String role;

    public @NotEmpty(message = "Username must not be empty") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username must not be empty") String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "Malformed email") @NotNull(message = "Email must not be null") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Malformed email") @NotNull(message = "Email must not be null") String email) {
        this.email = email;
    }

    public @NotNull(message = "Password must not be null") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Password must not be null") String password) {
        this.password = password;
    }

    public @NotNull(message = "Role can not be null") @Pattern(regexp = "ADMIN|MANAGER|USER", message = "The role must be ADMIN, MANAGER or USER") String getRole() {
        return role;
    }

    public void setRole(@NotNull(message = "Role can not be null") @Pattern(regexp = "ADMIN|MANAGER|USER", message = "The role must be ADMIN, MANAGER or USER") String role) {
        this.role = role;
    }
}
