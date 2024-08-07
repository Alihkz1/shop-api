package com.shop.controller;

import com.shop.command.ChangePasswordCommand;
import com.shop.command.UserEditCommand;
import com.shop.command.UserLoginCommand;
import com.shop.command.UserSignUpCommand;
import com.shop.service.UserService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "signup")
    public ResponseEntity<Response> signUp(@RequestBody UserSignUpCommand command) {
        return userService.signUp(command);
    }

    @PostMapping(path = "login")
    public ResponseEntity<Response> login(@RequestBody UserLoginCommand command) {
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

    @GetMapping(path = "retrieve")
    public ResponseEntity<Response> getById() {
        return userService.getById();
    }

    @DeleteMapping(path = "delete/{userId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long userId) {
        return userService.deleteById(userId);
    }

    @PutMapping(path = "change-password")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordCommand command) {
        return userService.changePassword(command);
    }
}
