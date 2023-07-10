package spigi.blog.service.impl;

import spigi.blog.model.Post;
import spigi.blog.repository.PostRepository;
import spigi.blog.service.PostService;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        validatePost(post);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        validatePost(post);
        return postRepository.save(post);
    }

    private void validatePost(Post post) {
        // TODO
    }
}
