package spigi.blog.validations;

import org.springframework.stereotype.Component;
import spigi.blog.exception.ValidationException;
import spigi.blog.model.Post;
import spigi.blog.repository.UserRepository;

@Component
public class PostValidator {
    private UserRepository userRepository;

    public PostValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validatePost(Post post) {
        validateTitle(post);
        validateContent(post);
        validateSummary(post);
        validateThumbnailUrl(post);
        validateUserId(post);
    }

    private void validateTitle(Post post) {
        String title = post.getTitle();
        if (title == null || title.isEmpty()) {
            throw new ValidationException("Title is required");
        }
        if (title.length() > 75) {
            throw new ValidationException("Title must be less than 75 characters");
        }
        if (!title.matches("^[a-zA-Z0-9 ]*$")) {
            throw new ValidationException("Title must only contain letters, numbers, and spaces");
        }
    }

    private void validateContent(Post post) {
        String content = post.getContent();
        if (content == null || content.isEmpty()) {
            throw new ValidationException("Content is required");
        }
    }

    private void validateSummary(Post post) {
        String summary = post.getSummary();
        if (summary == null || summary.isEmpty()) {
            throw new ValidationException("Summary is required");
        }
    }

    private void validateThumbnailUrl(Post post) {
        String thumbnailUrl = post.getThumbnailUrl();
        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            throw new ValidationException("Thumbnail URL is required");
        }
        if (!thumbnailUrl.matches("^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)$")) {
            throw new ValidationException("Thumbnail URL is invalid");
        }
    }

    private void validateUserId(Post post) {
        Long userId = post.getUser().getId();
        if (userId == null) {
            throw new ValidationException("User ID is required");
        }
        if (!userRepository.existsById(userId)) {
            throw new ValidationException("User not found");
        }
    }

}
