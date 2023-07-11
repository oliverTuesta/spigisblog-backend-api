package spigi.blog.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String profileBackgroundUrl;
    private String profileDescription;
    private String websiteUrl;
}
