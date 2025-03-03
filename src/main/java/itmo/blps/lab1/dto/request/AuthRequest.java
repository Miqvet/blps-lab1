package itmo.blps.lab1.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
