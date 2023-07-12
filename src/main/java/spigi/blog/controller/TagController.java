package spigi.blog.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spigi.blog.dto.category.CategoryCreationDto;
import spigi.blog.dto.category.CategoryResponseDto;
import spigi.blog.dto.tag.TagCreationDto;
import spigi.blog.dto.tag.TagResponseDto;
import spigi.blog.model.Category;
import spigi.blog.model.Tag;
import spigi.blog.service.CategoryService;
import spigi.blog.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/blog/v1/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // URL: http://localhost:8080/api/blog/v1/tags
    // Method: GET
    @Transactional
    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    // URL: http://localhost:8080/api/blog/v1/tags/{id}
    // Method: GET
    @Transactional
    @GetMapping("{id}")
    public ResponseEntity<TagResponseDto> getTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    // URL: http://localhost:8080/api/blog/v1/tags/{id}
    // Method: PUT
    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody TagCreationDto tag) {
        return ResponseEntity.ok(tagService.updateTag(id, tag));
    }

    // URL: http://localhost:8080/api/blog/v1/tags
    // Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody TagCreationDto tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(tag));
    }

    // URL: http://localhost:8080/api/blog/v1/tags/{id}
    // Method: DELETE
    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }


}
