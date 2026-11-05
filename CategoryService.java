
package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.CategoryRequest;
import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Transactional
    public Category addCategory(CategoryRequest request) {

        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setActive(true);

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, CategoryRequest request) {

        Category category = getCategoryById(id);

        if (!category.getCategoryName().equalsIgnoreCase(request.getCategoryName())
                && categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new RuntimeException("Category already exists");
        }

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
