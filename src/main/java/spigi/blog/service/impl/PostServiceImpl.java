package spigi.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spigi.blog.dto.post.PostCreationDto;
import spigi.blog.dto.post.PostResponseDto;
import spigi.blog.dto.post.PostUpdateDto;
import spigi.blog.exception.ResourceNotFoundException;
import spigi.blog.model.Category;
import spigi.blog.model.Post;
import spigi.blog.model.Tag;
import spigi.blog.model.User;
import spigi.blog.repository.CategoryRepository;
import spigi.blog.repository.PostRepository;
import spigi.blog.repository.TagRepository;
import spigi.blog.repository.UserRepository;
import spigi.blog.service.PostService;
import spigi.blog.validations.PostValidator;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final PostValidator postValidator;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper, PostValidator postValidator, TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.postValidator = postValidator;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Post createPost(PostCreationDto postDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);
        post.setViews(0L);
        post.setIsVisible(false);
        post.setSlug(post.getTitle().toLowerCase().replace(" ", "-"));
        postValidator.validatePost(post);
        post.setCreateDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public PostResponseDto updatePost(PostUpdateDto postDto, Long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        modelMapper.map(postDto, existingPost);
        postValidator.validatePost(existingPost);
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
    public PostResponseDto getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug);
        if (post == null)
            throw new ResourceNotFoundException("Post not found");
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

    @Override
    public void addTag(String postSlug, String tagSlug) {
        Post post = postRepository.findBySlug(postSlug);
        Tag tag = tagRepository.findBySlug(tagSlug);
        if (post == null)
            throw new ResourceNotFoundException("Post not found");
        if (tag == null)
            throw new ResourceNotFoundException("Tag not found");

        post.getTags().add(tag);
        tag.getPosts().add(post);

        postRepository.save(post);
        tagRepository.save(tag);
    }

    @Override
    public void removeTag(String postSlug, String tagSlug) {
        Post post = postRepository.findBySlug(postSlug);
        Tag tag = tagRepository.findBySlug(tagSlug);
        if (post == null)
            throw new ResourceNotFoundException("Post not found");
        if (tag == null)
            throw new ResourceNotFoundException("Tag not found");

        post.getTags().remove(tag);
        tag.getPosts().remove(post);

        postRepository.save(post);
        tagRepository.save(tag);
    }

    @Override
    public List<PostResponseDto> getPostsByTagSlug(String slug) {
        List<Post> posts = postRepository.findAllByTagsSlugAndIsVisibleTrue(slug);
        return posts.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).toList();
    }

    @Override
    public void addCategory(String postSlug, String categorySlug) {
        Post post = postRepository.findBySlug(postSlug);
        Category category = categoryRepository.findBySlug(categorySlug);
        if (post == null)
            throw new ResourceNotFoundException("Post not found");
        if (category == null)
            throw new ResourceNotFoundException("Category not found");

        post.getCategories().add(category);
        category.getPosts().add(post);

        postRepository.save(post);
        categoryRepository.save(category);
    }

    @Override
    public void removeCategory(String postSlug, String categorySlug) {
        Post post = postRepository.findBySlug(postSlug);
        Category category = categoryRepository.findBySlug(categorySlug);
        if (post == null)
            throw new ResourceNotFoundException("Post not found");
        if (category == null)
            throw new ResourceNotFoundException("Category not found");

        post.getCategories().remove(category);
        category.getPosts().remove(post);

        postRepository.save(post);
        categoryRepository.save(category);
    }

    @Override
    public List<PostResponseDto> getPostsByCategorySlug(String slug) {
        return null;
    }
}
