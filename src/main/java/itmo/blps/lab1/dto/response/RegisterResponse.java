package itmo.blps.lab1.dto.response;

import lombok.Data;

@Data
public class RegisterResponse {
    private String jwtToken;
    private String message;
}
