package itmo.blps.lab1.dto;

import itmo.blps.lab1.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
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
