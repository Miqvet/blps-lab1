package itmo.blps.lab1.controller;

import itmo.blps.lab1.entity.Category;
import itmo.blps.lab1.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    CategoryService categoryService;
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody String categoryName) {
        return ResponseEntity.ok(categoryService.create(categoryName));
    }
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }
}
