package spigi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spigi.blog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
