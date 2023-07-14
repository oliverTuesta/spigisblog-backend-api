package spigi.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spigi.blog.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}
