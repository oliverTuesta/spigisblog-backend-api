package spigi.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spigi.blog.dto.category.CategoryCreationDto;
import spigi.blog.dto.category.CategoryResponseDto;
import spigi.blog.exception.ResourceNotFoundException;
import spigi.blog.model.Category;
import spigi.blog.repository.CategoryRepository;
import spigi.blog.service.CategoryService;
import spigi.blog.validations.CategoryValidator;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private CategoryValidator categoryValidator;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.categoryValidator = categoryValidator;
    }

    @Override
    public Category createCategory(CategoryCreationDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setSlug(categoryDto.getName().toLowerCase().replace(" ", "-"));
        categoryValidator.validateCategoryCreation(category);
        return categoryRepository.save(category);
    }

    @Override
    public CategoryResponseDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    @Override
    public Category updateCategory(Long id, CategoryCreationDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryValidator.validateCategoryUpdate(existingCategory, modelMapper.map(categoryDto, Category.class));
        modelMapper.map(categoryDto, existingCategory);
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return modelMapper.map(categoryRepository.findAll(), List.class);
    }
}
