package itmo.blps.lab1.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.converters.UserConverter;
import itmo.blps.lab1.dto.request.AuthRequest;
import itmo.blps.lab1.dto.request.RegisterRequest;
import itmo.blps.lab1.dto.response.AuthResponse;
import itmo.blps.lab1.mail.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> register(
            @Parameter(description = "Данные для регистрации пользователя", required = true)
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(UserConverter.toDTO(authService.registerUser(request)));
    }

    @Operation(summary = "Вход пользователя",
            description = "Аутентифицирует пользователя по предоставленным учетным данным.")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Данные для входа пользователя", required = true)
            @Valid @RequestBody AuthRequest request,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(), HttpStatus.BAD_REQUEST);
        }

        String token = authService.authenticateUser(request);
        return ResponseEntity.ok(new AuthResponse(token, "Success"));
    }
}
