package dtn.com.auth_service.model;

public enum Role {
    USER,
    ADMIN,
    MODERATOR;

    public static Role toEnum(String role) {
        for (Role item : Role.values()) {
            if (item.toString().equals(role)) return item;
        }
        return null;
    }
}