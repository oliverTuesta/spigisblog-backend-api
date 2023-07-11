package spigi.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spigi.blog.dto.user.UserCreationDto;
import spigi.blog.dto.user.UserResponseDto;
import spigi.blog.dto.user.UserUpdateDto;
import spigi.blog.exception.ResourceNotFoundException;
import spigi.blog.exception.ValidationException;
import spigi.blog.model.User;
import spigi.blog.repository.UserRepository;
import spigi.blog.service.UserService;
import spigi.blog.validations.UserValidator;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }
    @Override
    public User createUser(UserCreationDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        userValidator.validateUserCreation(user);
        user.setRegistrationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserUpdateDto userDto, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userValidator.validateUserUpdate(existingUser, modelMapper.map(userDto, User.class));
        modelMapper.map(userDto, existingUser);
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserResponseDto.class);
    }

}
