package com.shop.service;

import com.shop.command.UserEditCommand;
import com.shop.command.UserLoginCommand;
import com.shop.command.UserSignUpCommand;
import com.shop.model.User;
import com.shop.repository.UserRepository;
import com.shop.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
                HashMap<String, User> data = new HashMap();
                data.put("user", userRepository.findByEmail(command.getEmail()).get());
                response.setData(data);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<Response<User>> login(UserLoginCommand command) {
        Response response = new Response();
        Optional<User> exist = userRepository.findByEmail(command.getEmail());
        if (exist.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("incorrect email!");
            return ResponseEntity.ok(response);
        }
        if (exist.isPresent()) {
            if (exist.get().getPassword().equals(command.getPassword())) {
                response.setSuccess(true);
                response.setMessage("logged in!");
                HashMap<String, User> data = new HashMap();
                data.put("user", exist.get());
                response.setData(data);
            } else {
                response.setSuccess(false);
                response.setMessage("incorrect password!");
            }
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> edit(UserEditCommand command) {
        Response response = new Response();
        Optional<User> user = userRepository.findByUserId(command.getUserId());

        if (user.isPresent()) {
            if (command.getEmail() != null) {
                user.get().setEmail(command.getEmail());
            }
            if (command.getName() != null) {
                user.get().setName(command.getName());
            }
            if (command.getPhone() != null) {
                user.get().setPhone(command.getPhone());
            }
            userRepository.save(user.get());
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("wrong userId");
            response.setSuccess(false);
            return ResponseEntity.ok(response);
        }
    }
}
