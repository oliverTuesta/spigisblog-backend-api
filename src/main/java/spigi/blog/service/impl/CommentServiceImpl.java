package spigi.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spigi.blog.dto.comment.CommentCreationDto;
import spigi.blog.dto.comment.CommentResponseDto;
import spigi.blog.model.Comment;
import spigi.blog.model.Post;
import spigi.blog.repository.CommentRepository;
import spigi.blog.repository.PostRepository;
import spigi.blog.service.CommentService;
import spigi.blog.validations.CommentValidator;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private CommentValidator commentValidator;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CommentValidator commentValidator, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Comment createComment(Long postId, Long parentId, CommentCreationDto commentCreationDto) {
        Comment comment = modelMapper.map(commentCreationDto, Comment.class);
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);
        if (parentId != null) {
            Comment parentComment = commentRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParent(parentComment);
        }
        comment.setCreateDate(LocalDateTime.now());
        commentValidator.validateComment(comment);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setAuthor("deleted");
        comment.setContent("deleted");
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponseDto> getComments(Long postId) {
        return commentRepository.findAllByPostId(postId).stream().map(comment -> modelMapper.map(comment, CommentResponseDto.class)).toList();
    }
}
