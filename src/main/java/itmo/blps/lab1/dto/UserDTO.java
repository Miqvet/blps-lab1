package itmo.blps.lab1.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String email;
    private String name;
    private String phoneNumber;
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }
}
