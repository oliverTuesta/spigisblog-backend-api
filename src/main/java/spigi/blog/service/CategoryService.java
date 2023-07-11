package spigi.blog.service;

import spigi.blog.dto.category.CategoryCreationDto;
import spigi.blog.dto.category.CategoryResponseDto;
import spigi.blog.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(CategoryCreationDto categoryDto);
    public CategoryResponseDto getCategory(Long id);
    public Category updateCategory(Long id, CategoryCreationDto categoryDto);
    public void deleteCategory(Long id);
    public List<CategoryResponseDto> getAllCategories();
}
