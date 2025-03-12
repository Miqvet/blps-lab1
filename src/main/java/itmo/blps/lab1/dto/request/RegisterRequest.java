package itmo.blps.lab1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Имя обязательно")
    @Size(min = 5, message = "Имя должно быть не менее 5 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я ]+$", message = "Имя может содержать только буквы и пробелы")
    private String name;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я]).+$",
            message = "Пароль должен содержать хотя бы одну цифру, заглавную и строчную букву"
    )
    private String password;

    @NotBlank(message = "Номер телефона обязателен")
    @Pattern(regexp = "^\\+?7\\d{10}$",
            message = "Номер должен быть в формате +7XXXXXXXXXX или 7XXXXXXXXXX")
    private String phoneNumber;
}
