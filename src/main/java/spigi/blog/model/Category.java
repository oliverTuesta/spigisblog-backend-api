package spigi.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false, length = 30)
    private String name;

    @Column(name="description", nullable = false, length = 100)
    private String description;

    @Column(name="slug", nullable = false, length = 30)
    private String slug;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Post> posts;
}
