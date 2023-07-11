package spigi.blog.service;

import spigi.blog.dto.PostDTO;
import spigi.blog.model.Post;

import java.util.List;

public interface PostService {
    public Post createPost(PostDTO postDto, Long userId);
    public Post updatePost(PostDTO postDto, Long id);
    public void deletePost(Long id);
    public void publishPost(Long id);
    public PostDTO getPost(Long id);
    public List<PostDTO> getPostsByUser(Long userId);
    public List<PostDTO> getAllPosts();
    public List<PostDTO> getPopularPosts();
    public List<PostDTO> getRecentPosts();
}
