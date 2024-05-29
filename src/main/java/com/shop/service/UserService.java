package com.shop.service;

import com.shop.command.UserLoginCommand;
import com.shop.command.UserSignUpCommand;
import com.shop.model.User;
import com.shop.repository.UserRepository;
import com.shop.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Response> signUp(UserSignUpCommand command) {
        Response response = new Response();
        try {
            Optional<User> exist = userRepository.findByEmail(command.getEmail());
            if (exist.isPresent()) {
                response.setSuccess(false);
                response.setMessage("email already taken!");
                return ResponseEntity.badRequest().body(response);
            } else {
                userRepository.save(command.toEntity());
                response.setSuccess(true);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<Response> login(UserLoginCommand command) {
        Response response = new Response();
        Optional<User> exist = userRepository.findByEmail(command.getEmail());
        if (exist.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("incorrect email!");
            return ResponseEntity.badRequest().body(response);
        }
        if (exist.isPresent()) {
            if (exist.get().getPassword().equals(command.getPassword())) {
                response.setSuccess(true);
                response.setMessage("logged in!");
            } else {
                response.setSuccess(false);
                response.setMessage("incorrect password!");
            }
        }
        return ResponseEntity.ok(response);
    }
}
