package spigi.blog.dto.comment;

import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private Long postId;
    private Long parentId;
}
