package spigi.blog.service;

import spigi.blog.dto.UserDTO;
import spigi.blog.model.User;

public interface UserService {
    public User createUser(UserDTO user);
    public User updateUser(UserDTO user, Long id);
    public void deleteUser(Long id);
    public UserDTO getUser(Long id);
}
