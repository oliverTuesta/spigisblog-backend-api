package spigi.blog.dto.post;

import lombok.Data;

@Data
public class PostUpdateDto {
    private String title;
    private String content;
    private String summary;
    private String thumbnailUrl;
}
