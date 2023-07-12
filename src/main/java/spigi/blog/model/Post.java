package spigi.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false, unique = true, length = 75)
    private String title;

    @Column(name="content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name="summary", nullable = false, columnDefinition = "TINYTEXT")
    private String summary;

    @Column(name="thumbnail_url", nullable = false, length = 255)
    private String thumbnailUrl;

    @Column(name="slug", nullable = false, unique = true, length = 75)
    private String slug;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = true)
    private LocalDateTime updateDate;

    @Column(name = "publish_date", nullable = true)
    private LocalDateTime publishDate;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "views", nullable = false)
    private Long views;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_POST_CATEGORIES_POST_ID")),
            inverseJoinColumns = @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_POST_CATEGORIES_CATEGORY_ID")))
    private List<Category> categories;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_POST_TAGS_POST_ID")),
            inverseJoinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "FK_POST_TAGS_TAG_ID")))
    private List<Tag> tags;

}
