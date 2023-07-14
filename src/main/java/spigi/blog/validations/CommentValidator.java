package spigi.blog.validations;

import org.springframework.stereotype.Component;
import spigi.blog.exception.ValidationException;
import spigi.blog.model.Comment;
import spigi.blog.repository.CommentRepository;
import spigi.blog.repository.PostRepository;

@Component
public class CommentValidator {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentValidator(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    private void validateCommentContent(Comment comment) {
        if (comment.getContent() == null || comment.getContent().isEmpty()) {
            throw new ValidationException("Comment content cannot be empty");
        }
    }

    private void validateCommentAuthor(Comment comment) {
        if (comment.getAuthor() == null || comment.getAuthor().isEmpty()) {
            comment.setAuthor("Anonymous");
        }
        if (comment.getAuthor().length() > 30) {
            throw new ValidationException("Comment author cannot be longer than 30 characters");
        }
        if (comment.getAuthor().length() < 3) {
            throw new ValidationException("Comment author cannot be shorter than 3 characters");
        }
        if (!comment.getAuthor().matches("[a-zA-Z0-9]+")) {
            throw new ValidationException("Comment author can only contain letters and numbers");
        }
    }

    private void validatePostExists(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ValidationException("Post does not exist");
        }
    }

    public void validateComment(Comment comment) {
        validateCommentContent(comment);
        validateCommentAuthor(comment);
        validatePostExists(comment.getPost().getId());
    }
}
