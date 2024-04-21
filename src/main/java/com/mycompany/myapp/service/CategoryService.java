package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.service.dto.CategoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createOrUpdate(CategoryDTO categoryDTO) {
        Category category = new Category();

        if (categoryDTO.getId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryDTO.getId());
            if (categoryOptional.isEmpty()) {
                throw new RuntimeException("Không tồn tại category này");
            }
            category = categoryOptional.get();
        } else { // tạo mới nếu chưa tồn tại
            category = new Category();
        }
        category.setName(categoryDTO.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    // láy ra danh sách
    public Page<Category> getListCategory(Pageable pageable, String search) {
        return categoryRepository.findAllByNameContainingIgnoreCase(pageable, search);
    }

    // láy ra danh sách
    public Page<Category> getListCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    // lấy chi tiết 1
    public Optional<Category> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
