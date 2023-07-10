package spigi.blog.service;

import spigi.blog.model.Post;

public interface PostService {
    public Post createPost(Post post);
    public Post updatePost(Post post);
}
