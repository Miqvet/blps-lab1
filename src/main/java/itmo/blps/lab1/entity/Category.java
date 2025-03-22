package itmo.blps.lab1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9 ]+$", message = "Категория должна состоять из цифр букв и может быть пробелов")
    @Column(nullable = false, unique = true, length = 100)
    private String name;
}
