package itmo.blps.lab1.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.dto.request.AuthRequest;
import itmo.blps.lab1.dto.request.RegisterRequest;
import itmo.blps.lab1.dto.response.AuthResponse;
import itmo.blps.lab1.entity.User;
import itmo.blps.lab1.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Аутентификация", description = "API для регистрации и входа пользователей")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя",
            description = "Создает нового пользователя на основе переданных данных.")
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Parameter(description = "Данные для регистрации пользователя", required = true)
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.registerUser(request));
    }

    @Operation(summary = "Вход пользователя",
            description = "Аутентифицирует пользователя по предоставленным учетным данным.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Данные для входа пользователя", required = true)
            @RequestBody AuthRequest request) {

        String token = authService.authenticateUser(request);
        return ResponseEntity.ok(new AuthResponse(token, "Success"));
    }
}
