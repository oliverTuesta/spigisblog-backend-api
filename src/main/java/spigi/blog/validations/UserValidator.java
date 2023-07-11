package spigi.blog.validations;

import org.springframework.stereotype.Component;
import spigi.blog.exception.ValidationException;
import spigi.blog.model.User;
import spigi.blog.repository.UserRepository;

@Component
public class UserValidator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void validateUser(User user) {
        validateUsername(user.getUsername());
        validateUsernameLength(user.getUsername());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validateProfilePictureUrl(user.getProfilePictureUrl());
        validatePasswordLength(user.getPassword());
        validateEmailFormat(user.getEmail());
        validateFirstNameLength(user.getFirstName());
        validateLastNameLength(user.getLastName());
    }

    public void validateUserCreation(User user) {
        validateUniqueUsername(user.getUsername());
        validateUniqueEmail(user.getEmail());
        validateUser(user);
    }

    public void validateUserUpdate(User currentUser, User updatedUser) {
        if (!currentUser.getUsername().equals(updatedUser.getUsername())) {
            validateUniqueUsername(updatedUser.getUsername());
        }
        if (!currentUser.getEmail().equals(updatedUser.getEmail())) {
            validateUniqueEmail(updatedUser.getEmail());
        }
        validateUser(updatedUser);
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username is required");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new ValidationException("Email is required");
        }
    }

    private void validateProfilePictureUrl(String profilePictureUrl) {
        if (profilePictureUrl != null && !profilePictureUrl.matches("^(http(s?):/)(/[^/]+)+\\.(?:jpg|jpeg|png)$")) {
            throw new ValidationException("Profile picture URL has the wrong format");
        }
    }

    private void validateUsernameLength(String username) {
        if (username.length() < 4 || username.length() > 20) {
            throw new ValidationException("Username must be between 4 and 20 characters long");
        }
    }

    private void validatePasswordLength(String password) {
        if (password.length() < 5 || password.length() > 100) {
            throw new ValidationException("Password must be between 5 and 100 characters long");
        }
    }

    private void validateEmailFormat(String email) {
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Email has the wrong format");
        }
    }

    private void validateUniqueUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ValidationException("Username is already taken");
        }
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ValidationException("Email is already taken");
        }
    }

    private void validateFirstNameLength(String firstName) {
        if (firstName != null && firstName.length() > 100) {
            throw new ValidationException("First name must be less than 100 characters long");
        }
    }

    private void validateLastNameLength(String lastName) {
        if (lastName != null && lastName.length() > 100) {
            throw new ValidationException("Last name must be less than 100 characters long");
        }
    }
}

