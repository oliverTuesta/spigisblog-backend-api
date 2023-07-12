package spigi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spigi.blog.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    boolean existsBySlug(String slug);
    Category findBySlug(String slug);
}
