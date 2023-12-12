package com.myfood.services;

import com.myfood.domain.user.*;
import com.myfood.exceptions.EmailAlreadyExistsException;
import com.myfood.exceptions.NotAuthorizedException;
import com.myfood.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    private UserDTO registerUser(UserRegisterDTO data, UUID companyId) {
        var emailExists = repository.findByEmail(data.email());
        if (emailExists.isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        var encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        var newUser = new User(data.name(), data.email(), encryptedPassword, data.role(), companyId);
        var createdUser = repository.save(newUser);
        try {
            emailService.sendNewUserEmail(data.email());
        } catch (Exception ex) {

        }
        return new UserDTO(createdUser.getName(), createdUser.getEmail(), createdUser.getRole(), createdUser.getCompanyId(), createdUser.getCreatedAt());
    }

    public UserDTO register(UserRegisterDTO data) {
        return this.register(data, null);
    }

    public UserDTO register(UserRegisterDTO data, UUID companyId) {
        if (data.role() == UserRole.ADMIN) {
            throw new NotAuthorizedException("User not authorized");
        }
        return registerUser(data, companyId);
    }

    public UserDTO registerAdmmin(UserRegisterDTO data, UUID companyId) {
        return registerUser(data, companyId);
    }

    public Optional<UserDetails> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void updatePassword(UpdatePasswordDTO data) {
        if (!data.newPassword().equals(data.confirmPassword())) {
            throw new NotAuthorizedException("New password and confirm password should be equals");
        }
        var emailExists = repository.findByEmail(data.email());
        if (emailExists.isEmpty()) {
            throw new NotAuthorizedException("Username or password invalid");
        }
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.oldPassword());
        var auth = authenticationManager.authenticate(usernamePassword);
        if (!auth.isAuthenticated()) {
            throw new NotAuthorizedException("Username or password invalid");
        }
        var user = (User) auth.getPrincipal();
        var encryptedPassword = new BCryptPasswordEncoder().encode(data.newPassword());
        user.setPassword(encryptedPassword);
        repository.save(user);
        try {
            emailService.sendPasswordChangedEmail(data.email());
        } catch (Exception ex) {

        }
    }
}
