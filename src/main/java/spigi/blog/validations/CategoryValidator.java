package spigi.blog.validations;

import org.springframework.stereotype.Component;
import spigi.blog.exception.ValidationException;
import spigi.blog.model.Category;
import spigi.blog.repository.CategoryRepository;

@Component
public class CategoryValidator {

    private CategoryRepository categoryRepository;

    public CategoryValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name is required");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new ValidationException("Description is required");
        }
    }

    private void validateNameLength(String name) {
        if (name.length() > 30) {
            throw new ValidationException("Name is maximum 30");
        }
    }

    private void validateNameUnique(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new ValidationException("Name must be unique");
        }
    }

    private void validateNameCharacters(String name) {
        if (!name.matches("^[a-zA-Z0-9 -]*$")) {
            throw new ValidationException("Name must only contain letters, numbers, spaces, and dashes");
        }
    }

    private void validateDescriptionLength(String description) {
        if (description.length() > 100) {
            throw new ValidationException("Description is maximum 100");
        }
    }

    private void validateSlug(String slug) {
        if (slug == null || slug.isEmpty()) {
            throw new ValidationException("Slug is required");
        }
        if (categoryRepository.existsBySlug(slug)) {
            throw new ValidationException("Slug must be unique");
        }
        if (!slug.matches("^[a-zA-Z0-9 -]*$")) {
            throw new ValidationException("Slug must only contain letters, numbers, spaces, and dashes");
        }
        if (slug.length() > 30) {
            throw new ValidationException("Slug is maximum 30");
        }
    }

    public void validateCategoryCreation(Category category) {
        validateName(category.getName());
        validateNameUnique(category.getName());
        validateNameCharacters(category.getName());
        validateDescription(category.getDescription());
        validateNameLength(category.getName());
        validateDescriptionLength(category.getDescription());
        validateSlug(category.getSlug());
    }

    public void validateCategoryUpdate(Category existingCategory, Category category) {
        validateName(category.getName());
        if (!existingCategory.getName().equals(category.getName())) {
            validateNameUnique(category.getName());
        }
        validateNameCharacters(category.getName());
        validateDescription(category.getDescription());
        validateNameLength(category.getName());
        validateDescriptionLength(category.getDescription());
    }
}
