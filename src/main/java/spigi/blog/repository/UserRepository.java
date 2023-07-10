package spigi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spigi.blog.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
