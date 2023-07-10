package spigi.blog.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String profileBackgroundUrl;
    private String profileDescription;
    private String websiteUrl;
}
