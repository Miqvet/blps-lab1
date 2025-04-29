package itmo.blps.lab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.converters.ProductConverter;
import itmo.blps.lab1.dto.ProductDTO;
import itmo.blps.lab1.dto.request.CreateProductRequest;
import itmo.blps.lab1.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    public ResponseEntity<List<ProductDTO>> getProducts(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<ProductDTO> products = productService.getAllProducts(pageable).stream()
                .map(ProductConverter::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }


    @Operation(summary = "Добавить товар",
            description = "Создает новый товар на основе переданных данных.")
    @PostMapping()
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    public ResponseEntity<ProductDTO> addProducts(
            @Valid @RequestBody CreateProductRequest createProductRequest) {
//        if(createProductRequest.getPrice().compareTo(BigDecimal.ONE) < 0){
//            throw new RuntimeException("Цена должна быть больше 0");
//        }
//        if(createProductRequest.getStock() < 0){
//            throw new RuntimeException("Кол-во должно быть больше 0");
//        }
        return ResponseEntity.ok(ProductConverter.toDTO(productService.addProduct(createProductRequest)));
    }
}

