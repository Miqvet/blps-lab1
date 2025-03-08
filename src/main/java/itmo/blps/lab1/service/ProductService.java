package itmo.blps.lab1.service;

import itmo.blps.lab1.converters.ProductConverter;
import itmo.blps.lab1.dto.ProductDTO;
import itmo.blps.lab1.dto.request.CreateProductRequest;
import itmo.blps.lab1.entity.Category;
import itmo.blps.lab1.entity.Product;
import itmo.blps.lab1.repository.CategoryRepository;
import itmo.blps.lab1.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(CreateProductRequest createProductRequest){
        Category category = categoryRepository.findByName(createProductRequest.getCategoryName()).orElseThrow(()
                -> new NoSuchElementException("Такой категории нет"));
        Product product = new Product();
        product.setStock(createProductRequest.getStock());
        product.setPrice(createProductRequest.getPrice());
        product.setDescription(createProductRequest.getDescription());
        product.setName(createProductRequest.getName());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(UUID productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategory_Name(categoryName);
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}

