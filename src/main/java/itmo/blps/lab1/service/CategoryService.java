package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Category;
import itmo.blps.lab1.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    public Category getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new NoSuchElementException("No Category found with name: " + name));
    }
    public Category create(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }
}
