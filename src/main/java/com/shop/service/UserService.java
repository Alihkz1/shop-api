package com.shop.service;

import com.shop.command.ChangePasswordCommand;
import com.shop.command.UserEditCommand;
import com.shop.command.UserLoginCommand;
import com.shop.command.UserSignUpCommand;
import com.shop.config.JWTService;
import com.shop.dto.AuthDto;
import com.shop.dto.UserDto;
import com.shop.dto.UserDtoMapper;
import com.shop.model.User;
import com.shop.repository.UserRepository;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserService(
            JWTService jwtService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserDtoMapper userDtoMapper
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoMapper = userDtoMapper;
    }

    public ResponseEntity<Response> signUp(UserSignUpCommand command) {
        Response response = new Response();
        try {
            Optional<User> existByEmail = userRepository.findByEmail(command.getEmail());
            Optional<User> existByPhone = userRepository.findByPhone(command.getPhone());
            if (existByEmail.isPresent()) {
                response.setSuccess(false);
                response.setMessage("ایمیل وارد شده قبلا قبت نام کرده است");
                return ResponseEntity.badRequest().body(response);
            } else if (existByPhone.isPresent()) {
                response.setSuccess(false);
                response.setMessage("تلفن همراه وارد شده قبلا ثبت نام کرده است");
                return ResponseEntity.badRequest().body(response);
            } else {
                userRepository.save(command.toEntity(passwordEncoder.encode(command.getPassword())));
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

    public ResponseEntity<Response<AuthDto>> login(UserLoginCommand command) {
        Response response = new Response();
        User userInDB = userRepository.login(command.getEmailOrPhone());

        if (userInDB == null) {
            response.setSuccess(false);
            response.setMessage("ایمیل یا شماره همراه اشتباه است");
            return ResponseEntity.ok(response);
        } else {
            boolean passwordMatches = passwordEncoder.matches(command.getPassword(), userInDB.getPassword());

            if (passwordMatches) {
                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("userId", userInDB.getUserId());
                var token = jwtService.generateToken(userInDB, extraClaims);

                AuthDto authDto = AuthDto.builder().token(token).user(userDtoMapper.apply(userInDB)).build();
                response.setData(authDto);
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

    public ResponseEntity<Response> getAll() {
        /*todo: create dto*/
        Response response = new Response();
        Map<String, List<User>> map = new HashMap<>();
        map.put("users", userRepository.findAll());
        response.setData(map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getById(Long userId) {
        /*todo: create dto*/
        Response response = new Response();
        Map<String, User> map = new HashMap<>();
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            map.put("user", user.get());
            response.setData(map);
        } else {
            response.setMessage("user not found! wrong userId.");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> deleteById(Long userId) {
        Response response = new Response();
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> changePassowrd(ChangePasswordCommand command) {
        Response response = new Response();
        Optional<User> user = userRepository.findByUserId(command.getUserId());
        if (user.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("no users found!");
        } else {
            user.get().setPassword(passwordEncoder.encode(command.getNewPassword()));
            userRepository.save(user.get());
        }
        return ResponseEntity.ok(response);
    }
}
