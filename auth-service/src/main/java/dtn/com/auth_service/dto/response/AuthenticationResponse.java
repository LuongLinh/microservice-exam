package dtn.com.auth_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    private int status;
    private String message;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    // Constructor không tham số (bắt buộc nếu bạn muốn Jackson deserialize)
    public AuthenticationResponse() {}

    // Constructor đầy đủ
    public AuthenticationResponse(int status, String message, int userId, String accessToken, String refreshToken) {
        this.status = status;
        this.message = message;
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters & Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
