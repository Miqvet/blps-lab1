package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Product;
import itmo.blps.lab1.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
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

