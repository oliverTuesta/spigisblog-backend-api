package spigi.blog.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spigi.blog.dto.user.UserCreationDto;
import spigi.blog.dto.user.UserResponseDto;
import spigi.blog.dto.user.UserUpdateDto;
import spigi.blog.model.User;
import spigi.blog.service.UserService;

@RestController
@RequestMapping("/api/blog/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // URL: http://localhost:8080/api/blog/v1/users
    // Method: POST
    @Transactional
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserCreationDto userDto) {
        return new ResponseEntity<User>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/blog/v1/users/{id}
    // Method: PUT
    @Transactional
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto userDto, @PathVariable(value = "id") Long id) {
        return new ResponseEntity<User>(userService.updateUser(userDto, id), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/users/{id}
    // Method: DELETE
    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/blog/v1/users/{id}
    // Method: GET
    @Transactional
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<UserResponseDto>(userService.getUser(id), HttpStatus.OK);
    }
}
