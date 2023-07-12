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
@Table(name="tags")
public class Tag {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false, length = 30)
    private String name;

    @Column(name="slug", nullable = false, length = 30, unique = true)
    private String slug;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

}
