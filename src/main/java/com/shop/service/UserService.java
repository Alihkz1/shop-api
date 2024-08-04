package com.shop.service;

import com.shop.command.ChangePasswordCommand;
import com.shop.command.UserEditCommand;
import com.shop.command.UserLoginCommand;
import com.shop.command.UserSignUpCommand;
import com.shop.config.JWTService;
import com.shop.dto.AuthDto;
import com.shop.dto.UserDtoMapper;
import com.shop.model.User;
import com.shop.repository.UserRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import com.shop.shared.enums.ErrorMessagesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService extends BaseService {


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
        try {
            Optional<User> existByEmail = userRepository.findByEmail(command.getEmail());
            Optional<User> existByPhone = userRepository.findByPhone(command.getPhone());
            if (existByEmail.isPresent()) {
                return badRequestResponse(ErrorMessagesEnum.EMAIL_ALREADY_REGISTERED);
            } else if (existByPhone.isPresent()) {
                return badRequestResponse(ErrorMessagesEnum.PHONE_ALREADY_REGISTERED);
            } else {
                userRepository.save(command.toEntity(passwordEncoder.encode(command.getPassword())));
                UserLoginCommand loginCommand = UserLoginCommand.builder()
                        .emailOrPhone(command.getEmail())
                        .password(passwordEncoder.encode(command.getPassword()))
                        .build();
                return successResponse(loginAfterSignup(loginCommand));
            }
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public AuthDto loginAfterSignup(UserLoginCommand command) {
        User userInDB = userRepository.login(command.getEmailOrPhone());
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userInDB.getUserId());
        var token = jwtService.generateToken(userInDB, extraClaims);
        userRepository.updateLoginCountByUserId(userInDB.getUserId());
        AuthDto authDto = AuthDto.builder().token(token).user(userDtoMapper.apply(userInDB)).build();
        return authDto;
    }

    public ResponseEntity<Response> login(UserLoginCommand command) {
        User userInDB = userRepository.login(command.getEmailOrPhone());
        if (userInDB == null) {
            return badRequestResponse(ErrorMessagesEnum.EMAIL_OR_PHONE_INVALID);
        } else {
            boolean passwordMatches = passwordEncoder.matches(command.getPassword(), userInDB.getPassword());
            if (passwordMatches) {
                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("userId", userInDB.getUserId());
                var token = jwtService.generateToken(userInDB, extraClaims);
                userRepository.updateLoginCountByUserId(userInDB.getUserId());
                AuthDto authDto = AuthDto.builder().token(token).user(userDtoMapper.apply(userInDB)).build();
                return successResponse(authDto);
            } else {
                return badRequestResponse(ErrorMessagesEnum.PASSWORD_INVALID);
            }
        }
    }

    public ResponseEntity<Response> edit(UserEditCommand command) {
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
            return successResponse();
        } else {
            return badRequestResponse(ErrorMessagesEnum.NO_USERS_FOUND);
        }
    }

    public ResponseEntity<Response> getAll() {
        /*todo: create dto*/
        Map<String, List<User>> map = new HashMap<>();
        map.put("users", userRepository.getAll());
        return successResponse(map);
    }

    public ResponseEntity<Response> getById() {
        /*todo: create dto*/
        Map<String, User> map = new HashMap<>();
        Optional<User> user = userRepository.findByUserId(UserThread.getUserId());
        map.put("user", user.get());
        return successResponse(map);
    }

    public ResponseEntity<Response> deleteById(Long userId) {
        try {
            userRepository.deleteById(userId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> changePassword(ChangePasswordCommand command) {
        Optional<User> user = userRepository.findByUserId(command.getUserId());
        if (user.isEmpty()) {
            return badRequestResponse(ErrorMessagesEnum.NO_USERS_FOUND);
        } else {
            user.get().setPassword(passwordEncoder.encode(command.getNewPassword()));
            userRepository.save(user.get());
            return successResponse();
        }
    }
}
