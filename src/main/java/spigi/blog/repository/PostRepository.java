package spigi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spigi.blog.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
    List<Post> findAllByIsVisibleTrueOrderByViewsDesc();
    List<Post> findAllByIsVisibleTrueOrderByCreateDateDesc();
}
