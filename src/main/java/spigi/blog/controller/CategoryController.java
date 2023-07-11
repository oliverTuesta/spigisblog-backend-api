package spigi.blog.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spigi.blog.dto.category.CategoryCreationDto;
import spigi.blog.dto.category.CategoryResponseDto;
import spigi.blog.model.Category;
import spigi.blog.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/blog/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // URL: http://localhost:8080/api/blog/v1/categories
    // Method: GET
    @Transactional
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return new ResponseEntity<List<CategoryResponseDto>>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/categories/{id}
    // Method: GET
    @Transactional
    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CategoryResponseDto>(categoryService.getCategory(id), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/categories/{id}
    // Method: PUT
    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CategoryCreationDto categoryDto) {
        return new ResponseEntity<Category>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/categories
    // Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryCreationDto categoryDto) {
        return new ResponseEntity<Category>(categoryService.createCategory(categoryDto), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/categories/{id}
    // Method: DELETE
    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<String>("Category deleted successfully", HttpStatus.OK);
    }


}
