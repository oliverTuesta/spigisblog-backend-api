package spigi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spigi.blog.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
    Tag findBySlug(String slug);
}
