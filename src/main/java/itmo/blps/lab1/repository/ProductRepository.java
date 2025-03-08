package itmo.blps.lab1.repository;

import itmo.blps.lab1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory_Name(String categoryName);
    List<Product> findByNameContainingIgnoreCase(String name);
}
