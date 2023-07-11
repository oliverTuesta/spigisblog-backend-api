package spigi.blog.service;

import spigi.blog.dto.user.UserCreationDto;
import spigi.blog.dto.user.UserResponseDto;
import spigi.blog.dto.user.UserUpdateDto;
import spigi.blog.model.User;

public interface UserService {
    public User createUser(UserCreationDto user);
    public User updateUser(UserUpdateDto user, Long id);
    public void deleteUser(Long id);
    public UserResponseDto getUser(Long id);
}
