package itmo.blps.lab1.converters;

import itmo.blps.lab1.dto.ProductDTO;
import itmo.blps.lab1.entity.Category;
import itmo.blps.lab1.entity.Product;

public class ProductConverter {

    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategoryName(product.getCategory().getName());
        return dto;
    }

    public static Product fromDTO(ProductDTO dto, Category category) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        // Category should be set using a service or converter
        product.setCategory(category);
        return product;
    }
}
