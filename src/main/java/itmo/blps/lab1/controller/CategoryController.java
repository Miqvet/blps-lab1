package itmo.blps.lab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.entity.Category;
import itmo.blps.lab1.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Категории", description = "API для управления категориями")
@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Добавить категорию",
            description = "Создает новую категорию с указанным именем.")
    @PostMapping
    public ResponseEntity<Category> addCategory(
            @Parameter(description = "Имя категории", required = true)
            @RequestBody String categoryName) {

        return ResponseEntity.ok(categoryService.create(categoryName));
    }

    @Operation(summary = "Получить все категории",
            description = "Возвращает список всех категорий.")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {

        return ResponseEntity.ok(categoryService.getAll());
    }
}
