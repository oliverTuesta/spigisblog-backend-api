package spigi.blog.validations;

import org.springframework.stereotype.Component;
import spigi.blog.exception.ValidationException;
import spigi.blog.model.Category;
import spigi.blog.model.Tag;
import spigi.blog.repository.CategoryRepository;
import spigi.blog.repository.TagRepository;

@Component
public class TagValidator {

    private final TagRepository tagRepository;

    public TagValidator(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name is required");
        }
    }

    private void validateNameLength(String name) {
        if (name.length() > 30) {
            throw new ValidationException("Name is maximum 30");
        }
    }

    private void validateNameUnique(String name) {
        if (tagRepository.existsByName(name)) {
            throw new ValidationException("Name must be unique");
        }
    }

    private void validateNameCharacters(String name) {
        if (!name.matches("^[a-zA-Z0-9 -]*$")) {
            throw new ValidationException("Name must only contain letters, numbers, spaces, and dashes");
        }
    }

    private void validateSlug(String slug) {
        if (slug == null || slug.isEmpty()) {
            throw new ValidationException("Slug is required");
        }
        if (tagRepository.existsBySlug(slug)) {
            throw new ValidationException("Slug must be unique");
        }
        if (!slug.matches("^[a-zA-Z0-9 -]*$")) {
            throw new ValidationException("Slug must only contain letters, numbers, spaces, and dashes");
        }
        if (slug.length() > 30) {
            throw new ValidationException("Slug is maximum 30");
        }
    }

    public void validateTagCreation(Tag tag) {
        validateName(tag.getName());
        validateNameUnique(tag.getName());
        validateNameCharacters(tag.getName());
        validateNameLength(tag.getName());
        validateSlug(tag.getSlug());
    }

    public void validateTagUpdate(Tag existingTag, Tag tag) {
        validateName(tag.getName());
        if (!existingTag.getName().equals(tag.getName())) {
            validateNameUnique(tag.getName());
        }
        validateNameCharacters(tag.getName());
        validateNameLength(tag.getName());
    }
}
