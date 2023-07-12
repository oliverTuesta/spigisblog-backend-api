package spigi.blog.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spigi.blog.dto.post.PostCreationDto;
import spigi.blog.dto.post.PostResponseDto;
import spigi.blog.dto.post.PostUpdateDto;
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
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return new ResponseEntity<List<PostResponseDto>>(postService.getAllPosts(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/popular
    // Method: GET
    @Transactional
    @GetMapping("posts/popular")
    public ResponseEntity<List<PostResponseDto>> getPopularPosts() {
        return new ResponseEntity<List<PostResponseDto>>(postService.getPopularPosts(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/recent
    // Method: GET
    @Transactional
    @GetMapping("posts/recent")
    public ResponseEntity<List<PostResponseDto>> getRecentPosts() {
        return new ResponseEntity<List<PostResponseDto>>(postService.getRecentPosts(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/id/{id}
    // Method: GET
    @Transactional
    @GetMapping("posts/id/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<PostResponseDto>(postService.getPost(id), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/{id}
    // Method: POST
    @Transactional
    @PostMapping("posts/{id}")
    public ResponseEntity<Post> createPost(@RequestBody PostCreationDto postDto, @PathVariable(value = "id") Long userId) {
        return new ResponseEntity<Post>(postService.createPost(postDto, userId), HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/{id}
    // Method: PUT
    @Transactional
    @PutMapping("posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostUpdateDto postDto, @PathVariable(value = "id") Long id) {
        return new ResponseEntity<PostResponseDto>(postService.updatePost(postDto, id), HttpStatus.OK);
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

    // URL: http://localhost:8080/api/blog/v1/posts/user/id/{userId}
    // Method: GET
    @Transactional
    @GetMapping("users/id/{userId}/posts")
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@PathVariable(value = "userId") Long userId) {
        return new ResponseEntity<List<PostResponseDto>>(postService.getPostsByUser(userId), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/user/{username}/posts
    // Method: GET
    @Transactional
    @GetMapping("users/{username}/posts")
    public ResponseEntity<List<PostResponseDto>> getPostByUsername(@PathVariable(value = "username") String username) {
        return new ResponseEntity<List<PostResponseDto>>(postService.getPostByUsername(username), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/{slug}
    // Method: GET
    @Transactional
    @GetMapping("posts/{slug}")
    public ResponseEntity<PostResponseDto> getPostBySlug(@PathVariable(value = "slug") String slug) {
        return new ResponseEntity<PostResponseDto>(postService.getPostBySlug(slug), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/tag/{tagSlug}
    // Method: GET
    @Transactional
    @GetMapping("posts/tag/{tagSlug}")
    public ResponseEntity<List<PostResponseDto>> getPostsByTag(@PathVariable(value = "tagSlug") String tagSlug) {
        return new ResponseEntity<List<PostResponseDto>>(postService.getPostsByTagSlug(tagSlug), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/tag
    // Method: POST
    @Transactional
    @PostMapping("posts/tag")
    public ResponseEntity<?> addTagToPost(@RequestParam(value = "postSlug") String postSlug, @RequestParam(value = "tagSlug") String tagSlug) {
        postService.addTag(postSlug, tagSlug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/tag
    // Method: DELETE
    @Transactional
    @DeleteMapping("posts/tag")
    public ResponseEntity<?> removeTagFromPost(@RequestParam(value = "postSlug") String postSlug, @RequestParam(value = "tagSlug") String tagSlug) {
        postService.removeTag(postSlug, tagSlug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/category/{categorySlug}
    // Method: GET
    @Transactional
    @GetMapping("posts/category/{categorySlug}")
    public ResponseEntity<List<PostResponseDto>> getPostsByCategory(@PathVariable(value = "categorySlug") String categorySlug) {
        return new ResponseEntity<List<PostResponseDto>>(postService.getPostsByCategorySlug(categorySlug), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/category
    // Method: POST
    @Transactional
    @PostMapping("posts/category")
    public ResponseEntity<?> addCategoryToPost(@RequestParam(value = "postSlug") String postSlug, @RequestParam(value = "categorySlug") String categorySlug) {
        postService.addCategory(postSlug, categorySlug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/posts/category
    // Method: DELETE
    @Transactional
    @DeleteMapping("posts/category")
    public ResponseEntity<?> removeCategoryFromPost(@RequestParam(value = "postSlug") String postSlug, @RequestParam(value = "categorySlug") String categorySlug) {
        postService.removeCategory(postSlug, categorySlug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
