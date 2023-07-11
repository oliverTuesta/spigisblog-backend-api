package spigi.blog.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spigi.blog.dto.PostDTO;
import spigi.blog.model.Post;
import spigi.blog.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/blog/v1")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // URL: http://localhost:8080/api/blog/v1/posts
    // Method: GET
    @Transactional
    @GetMapping("posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return new ResponseEntity<List<PostDTO>>(postService.getAllPosts(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/popular
    // Method: GET
    @Transactional
    @GetMapping("posts/popular")
    public ResponseEntity<List<PostDTO>> getPopularPosts() {
        return new ResponseEntity<List<PostDTO>>(postService.getPopularPosts(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/recent
    // Method: GET
    @Transactional
    @GetMapping("posts/recent")
    public ResponseEntity<List<PostDTO>> getRecentPosts() {
        return new ResponseEntity<List<PostDTO>>(postService.getRecentPosts(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/{id}
    // Method: GET
    @Transactional
    @GetMapping("posts/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<PostDTO>(postService.getPost(id), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/{id}
    // Method: POST
    @Transactional
    @PostMapping("posts/{id}")
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDto, @PathVariable(value = "id") Long userId) {
        return new ResponseEntity<Post>(postService.createPost(postDto, userId), HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/{id}
    // Method: PUT
    @Transactional
    @PutMapping("posts/{id}")
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO postDto, @PathVariable(value = "id") Long id) {
        return new ResponseEntity<Post>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/{id}
    // Method: DELETE
    @Transactional
    @DeleteMapping("posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/{id}/publish
    // Method: PUT
    @Transactional
    @PutMapping("posts/{id}/publish")
    public ResponseEntity<?> publishPost(@PathVariable(value = "id") Long id) {
        postService.publishPost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/users/{userId}/posts
    // Method: GET
    @Transactional
    @GetMapping("users/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable(value = "userId") Long userId) {
        return new ResponseEntity<List<PostDTO>>(postService.getPostsByUser(userId), HttpStatus.OK);
    }



}
