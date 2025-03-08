package itmo.blps.lab1.controller;

import itmo.blps.lab1.converters.ProductConverter;
import itmo.blps.lab1.dto.ProductDTO;
import itmo.blps.lab1.dto.request.CreateProductRequest;
import itmo.blps.lab1.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    // Получить список товаров
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = productService.getAllProducts().stream()
                .map(ProductConverter::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> addProducts(@RequestBody CreateProductRequest createProductRequest){
        return ResponseEntity.ok(ProductConverter.toDTO(productService.addProduct(createProductRequest)));
    }
}

