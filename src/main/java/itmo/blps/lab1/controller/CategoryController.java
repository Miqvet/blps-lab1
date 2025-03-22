package itmo.blps.lab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.entity.Category;
import itmo.blps.lab1.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@Tag(name = "Категории", description = "API для управления категориями")
@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Добавить категорию",
            description = "Создает новую категорию с указанным именем.")
    @PostMapping
    public ResponseEntity<?> addCategory(
            @Parameter(description = "Имя категории", required = true)
            @RequestBody String categoryName) {

        String regex = "^[a-zA-Zа-яА-Я0-9 ]+$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(categoryName).matches()) {
            return new ResponseEntity<>("Категория должна состоять из цифр, букв и пробелов", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(categoryService.create(categoryName));
    }

    @Operation(summary = "Получить все категории",
            description = "Возвращает список всех категорий.")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }
}
