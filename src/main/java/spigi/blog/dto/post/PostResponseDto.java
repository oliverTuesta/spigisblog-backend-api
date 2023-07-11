package spigi.blog.dto.post;

import lombok.Data;
import spigi.blog.dto.user.UserResponseDto;

@Data
public class PostResponseDto {
    private String title;
    private String content;
    private String summary;
    private String thumbnailUrl;
    private String slug;
    private UserResponseDto user;
    private Long views;
}
