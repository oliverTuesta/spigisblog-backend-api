package spigi.blog.service;

import spigi.blog.dto.comment.CommentCreationDto;
import spigi.blog.dto.comment.CommentResponseDto;
import spigi.blog.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, Long parentId, CommentCreationDto commentDto);
    void deleteComment(Long id);
    List<CommentResponseDto> getComments(Long postId);
}
