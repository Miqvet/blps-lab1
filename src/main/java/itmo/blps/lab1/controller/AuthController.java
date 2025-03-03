package itmo.blps.lab1.controller;


import itmo.blps.lab1.service.AuthService;
import itmo.blps.lab1.dto.request.AuthRequest;
import itmo.blps.lab1.dto.request.RegisterRequest;
import itmo.blps.lab1.dto.response.AuthResponse;
import itmo.blps.lab1.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.authenticateUser(request);
        return ResponseEntity.ok(new AuthResponse(token, "все ок"));
    }

//    @PostMapping("/register")
//    @Operation(summary = "Регистрация пользователя", description = "Создание нового пользователя в системе")
//    @ApiResponse(
//            responseCode = "200",
//            description = "Пользователь успешно зарегистрирован",
//            content = @Content(schema = @Schema(implementation = User.class)))
//    @ApiResponse(
//            responseCode = "400",
//            description = "Некорректные данные запроса",
//            content = @Content)
//    public ResponseEntity<User> register(
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Данные для регистрации",
//                    required = true,
//                    content = @Content(schema = @Schema(implementation = RegisterRequest.class))
//            )
//            @RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(authService.registerUser(request));
//    }
//
//    @PostMapping("/login")
//    @Operation(summary = "Аутентификация пользователя", description = "Вход в систему и получение JWT токена")
//    @ApiResponse(
//            responseCode = "200",
//            description = "Успешная аутентификация",
//            content = @Content(schema = @Schema(implementation = AuthResponse.class)))
//    @ApiResponse(
//            responseCode = "401",
//            description = "Неверные учетные данные",
//            content = @Content)
//    public ResponseEntity<AuthResponse> login(
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Учетные данные пользователя",
//                    required = true,
//                    content = @Content(schema = @Schema(implementation = AuthRequest.class))
//            )
//            @RequestBody AuthRequest request) {
//        String token = authService.authenticateUser(request);
//        return ResponseEntity.ok(new AuthResponse(token, "все ок"));
//    }
}
