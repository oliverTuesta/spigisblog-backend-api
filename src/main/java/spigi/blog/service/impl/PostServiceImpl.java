package spigi.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spigi.blog.dto.post.PostCreationDto;
import spigi.blog.dto.post.PostResponseDto;
import spigi.blog.dto.post.PostUpdateDto;
import spigi.blog.exception.ResourceNotFoundException;
import spigi.blog.exception.ValidationException;
import spigi.blog.model.Post;
import spigi.blog.model.User;
import spigi.blog.repository.PostRepository;
import spigi.blog.repository.UserRepository;
import spigi.blog.service.PostService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public Post createPost(PostCreationDto postDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);
        post.setViews(0L);
        post.setIsVisible(false);
        post.setSlug(post.getTitle().toLowerCase().replace(" ", "-"));
        validatePost(post);
        post.setCreateDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public PostResponseDto updatePost(PostUpdateDto postDto, Long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        modelMapper.map(postDto, existingPost);
        validatePost(existingPost);
        if (existingPost.getIsVisible()) {
            existingPost.setUpdateDate(LocalDateTime.now());
        }
        return modelMapper.map(postRepository.save(existingPost), PostResponseDto.class);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void publishPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        post.setIsVisible(true);
        post.setPublishDate(LocalDateTime.now());
        postRepository.save(post);
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if (post.getIsVisible())
            post.setViews(post.getViews() + 1);
        return modelMapper.map(post, PostResponseDto.class);
    }

    @Override
    public List<PostResponseDto> getPostsByUser(Long userId) {
        return postRepository.findAllByUserId(userId).stream().map(post -> modelMapper.map(post, PostResponseDto.class)).toList();
    }

    @Override
    public List<PostResponseDto> getPostByUsername(String username) {
        return postRepository.findAllByUserUsernameAndIsVisibleTrue(username).stream().map(post -> modelMapper.map(post, PostResponseDto.class)).toList();
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream().map(post -> modelMapper.map(post, PostResponseDto.class)).toList();
    }

    @Override
    public List<PostResponseDto> getPopularPosts() {
        return postRepository.findAllByIsVisibleTrueOrderByViewsDesc().stream().map(post -> modelMapper.map(post, PostResponseDto.class)).toList();
    }

    @Override
    public List<PostResponseDto> getRecentPosts() {
        return postRepository.findAllByIsVisibleTrueOrderByCreateDateDesc().stream().map(post -> modelMapper.map(post, PostResponseDto.class)).toList();
    }

    private void validatePost(Post post) {
        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            throw new ValidationException("Title is required");
        }
        if (post.getTitle().length() > 75) {
            throw new ValidationException("Title must be less than 75 characters");
        }
        if (!post.getTitle().matches("^[a-zA-Z0-9 ]*$")) {
            throw new ValidationException("Title must only contain letters, numbers and spaces");
        }
        if (post.getContent() == null || post.getContent().isEmpty()) {
            throw new ValidationException("Content is required");
        }
        if (post.getSummary() == null || post.getSummary().isEmpty()) {
            throw new ValidationException("Summary is required");
        }
        if (post.getThumbnailUrl() == null || post.getThumbnailUrl().isEmpty()) {
            throw new ValidationException("Thumbnail URL is required");
        }
        if (!post.getThumbnailUrl().matches("^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)$")) {
            throw new ValidationException("Thumbnail URL is invalid");
        }
        if (post.getUser().getId() == null) {
            throw new ValidationException("User ID is required");
        }
        if (!userRepository.existsById(post.getUser().getId())) {
            throw new ValidationException("User not found");
        }
    }
}
