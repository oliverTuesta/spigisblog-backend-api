package spigi.blog.service;

import spigi.blog.dto.post.PostCreationDto;
import spigi.blog.dto.post.PostResponseDto;
import spigi.blog.dto.post.PostUpdateDto;
import spigi.blog.model.Post;

import java.util.List;

public interface PostService {
    public Post createPost(PostCreationDto postDto, Long userId);
    public PostResponseDto updatePost(PostUpdateDto postDto, Long id);
    public void deletePost(Long id);
    public void publishPost(Long id);
    public PostResponseDto getPost(Long id);
    public PostResponseDto getPostBySlug(String slug);
    public List<PostResponseDto> getPostsByUser(Long userId);
    public List<PostResponseDto> getPostByUsername(String username);
    public List<PostResponseDto> getAllPosts();
    public List<PostResponseDto> getPopularPosts();
    public List<PostResponseDto> getRecentPosts();
    public void addTag(String postSlug, String tagSlug);
    public void removeTag(String postSlug, String tagSlug);
    public List<PostResponseDto> getPostsByTagSlug(String slug);
    public void addCategory(String postSlug, String categorySlug);
    public void removeCategory(String postSlug, String categorySlug);
    public List<PostResponseDto> getPostsByCategorySlug(String slug);

}
