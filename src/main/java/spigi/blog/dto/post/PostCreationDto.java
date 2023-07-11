package spigi.blog.dto.post;

import lombok.Data;

@Data
public class PostCreationDto {
    private String title;
    private String content;
    private String summary;
    private String thumbnailUrl;
}
