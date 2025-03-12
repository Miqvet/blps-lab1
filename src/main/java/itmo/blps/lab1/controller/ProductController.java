package itmo.blps.lab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.converters.ProductConverter;
import itmo.blps.lab1.dto.ProductDTO;
import itmo.blps.lab1.dto.request.CreateProductRequest;
import itmo.blps.lab1.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Товары", description = "API для управления товарами")
@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Получить список товаров",
            description = "Возвращает список всех доступных товаров.")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = productService.getAllProducts().stream()
                .map(ProductConverter::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Добавить товар",
            description = "Создает новый товар на основе переданных данных.")
    @PostMapping()
    public ResponseEntity<ProductDTO> addProducts(
            @RequestBody CreateProductRequest createProductRequest) {

        return ResponseEntity.ok(ProductConverter.toDTO(productService.addProduct(createProductRequest)));
    }
}

