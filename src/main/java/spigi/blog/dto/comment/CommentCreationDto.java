package spigi.blog.dto.comment;

import lombok.Data;

@Data
public class CommentCreationDto {
    private String content;
    private String author;
}
