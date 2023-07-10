package spigi.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spigi.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
