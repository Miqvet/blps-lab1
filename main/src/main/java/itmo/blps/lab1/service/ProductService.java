package itmo.blps.lab1.service;

import itmo.blps.lab1.dto.request.CreateProductRequest;
import itmo.blps.lab1.entity.Category;
import itmo.blps.lab1.entity.Product;
import itmo.blps.lab1.exception.classes.ProductNotFoundException;
import itmo.blps.lab1.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    public Product addProduct(CreateProductRequest createProductRequest) {
        Category category = categoryService.getByName(createProductRequest.getCategoryName().toLowerCase());
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

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null for update");
        }

        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFoundException(product.getId());
        }

        return productRepository.save(product);
    }

    public boolean decreaseProductStock(UUID productId, int quantity) {
        int updatedRows = productRepository.decreaseStock(productId, quantity);
        return updatedRows > 0;
    }
}

