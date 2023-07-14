package spigi.blog.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spigi.blog.dto.comment.CommentCreationDto;
import spigi.blog.dto.comment.CommentResponseDto;
import spigi.blog.model.Comment;
import spigi.blog.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/blog/v1/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // URL: http://localhost:8080/api/blog/v1/comments/post/{postId}
    // Method: GET
    @Transactional
    @GetMapping("post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable(value = "postId") Long postId) {
        return new ResponseEntity<List<CommentResponseDto>>(commentService.getComments(postId), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/comments/{id}
    // Method: DELETE
    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "id") Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<String>("Comment deleted successfully", HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/comments/post/{postId}/parent/{parentId}
    // Method: POST
    @Transactional
    @PostMapping("post/{postId}/parent/{parentId}")
    public ResponseEntity<Comment> createComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "parentId") Long parentId,
            @RequestBody CommentCreationDto commentDto) {

        Long nullableParentId = (parentId.equals(0L)) ? null : parentId;
        return new ResponseEntity<Comment>(
                commentService.createComment(postId, nullableParentId, commentDto),
                HttpStatus.OK);
    }


}
