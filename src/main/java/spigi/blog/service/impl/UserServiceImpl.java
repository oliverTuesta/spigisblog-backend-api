package spigi.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spigi.blog.dto.UserDTO;
import spigi.blog.exception.ResourceNotFoundException;
import spigi.blog.exception.ValidationException;
import spigi.blog.model.User;
import spigi.blog.repository.UserRepository;
import spigi.blog.service.UserService;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public User createUser(UserDTO userDto) {
        validateUser(userDto);
        User user = modelMapper.map(userDto, User.class);
        user.setRegistrationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDTO userDto, Long id) {
        validateUser(userDto);
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        modelMapper.map(userDto, existingUser);
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    private void validateUser(UserDTO user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new ValidationException("Username is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (user.getProfilePictureUrl() != null && !user.getProfilePictureUrl().matches("^(http(s?):/)(/[^/]+)+\\.(?:jpg|jpeg|png)$")) {
            throw new ValidationException("Profile picture URL has the wrong format");
        }
        if (user.getUsername().length() < 4 || user.getUsername().length() > 20) {
            throw new ValidationException("Username must be between 4 and 20 characters long");
        }
        if (user.getPassword().length() < 5 || user.getPassword().length() > 100) {
            throw new ValidationException("Password must be between 5 and 100 characters long");
        }
        if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Email has the wrong format");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ValidationException("Username is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email is already taken");
        }
        if (user.getFirstName() != null && user.getFirstName().length() > 100) {
            throw new ValidationException("First name must be less than 100 characters long");
        }
        if (user.getLastName() != null && user.getLastName().length() > 100) {
            throw new ValidationException("Last name must be less than 100 characters long");
        }
    }
}
