package spigi.blog.dto;

import lombok.Data;
import spigi.blog.model.User;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private String title;
    private String content;
    private String summary;
    private String thumbnailUrl;
}
