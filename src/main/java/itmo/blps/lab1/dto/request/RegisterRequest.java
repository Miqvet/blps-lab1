package itmo.blps.lab1.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
}
