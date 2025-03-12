package itmo.blps.lab1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Size(min=5)
    @Size(min = 5, message = "Name must be at least 5 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can only contain letters and spaces")
    private String name;

    @Size(min=5)
    private String password;

    @Column(nullable = false, unique = true, length = 11)
    @Size(min = 11, max = 11, message = "Phone number must be 11 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number can only contain digits")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }
}
