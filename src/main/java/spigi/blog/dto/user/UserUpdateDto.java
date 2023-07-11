package spigi.blog.dto.user;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String profileBackgroundUrl;
    private String profileDescription;
    private String websiteUrl;
}
