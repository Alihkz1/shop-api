package com.shop.controller;

import com.shop.command.UserEditCommand;
import com.shop.command.UserLoginCommand;
import com.shop.command.UserSignUpCommand;
import com.shop.model.User;
import com.shop.service.UserService;
import com.shop.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "signup")
    public ResponseEntity<Response> signUp(@RequestBody UserSignUpCommand command) {
        return userService.signUp(command);
    }

    @PostMapping(path = "login")
    public ResponseEntity<Response<User>> login(@RequestBody UserLoginCommand command) {
        return userService.login(command);
    }

    @PutMapping(path = "edit")
    public ResponseEntity<Response> edit(@RequestBody UserEditCommand command) {
        return userService.edit(command);
    }

    @GetMapping(path = "list")
    public ResponseEntity<Response> getAll() {
        return userService.getAll();
    }

    @GetMapping(path = "retrieve/{userId}")
    public ResponseEntity<Response> getById(@PathVariable Long userId) {
        return userService.getById(userId);
    }    ;
}
