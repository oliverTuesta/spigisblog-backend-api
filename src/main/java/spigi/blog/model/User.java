package spigi.blog.model;

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
@Table(name="users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", nullable = false, unique = true, length = 20)
    private String username;

    @Column(name="password", nullable = false, length = 100)
    private String password;

    @Column(name="email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "profile_picture_url", nullable = true, length = 255)
    private String profilePictureUrl;

    @Column(name = "profile_background_url", nullable = true, length = 255)
    private String profileBackgroundUrl;

    @Column(name = "profile_description", nullable = true, length = 150)
    private String profileDescription;

    @Column(name = "website_url", nullable = true, length = 150)
    private String websiteUrl;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "last_login_date", nullable = true)
    private LocalDateTime lastLoginDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Post> posts;

}
