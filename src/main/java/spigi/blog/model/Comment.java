package spigi.blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "author", nullable = false, length = 30)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true, foreignKey = @ForeignKey(name = "FK_PARENT_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Comment parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "FK_POST_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Post post;
}

