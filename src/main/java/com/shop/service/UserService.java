package com.shop.service;

import com.shop.command.ChangePasswordCommand;
import com.shop.command.UserEditCommand;
import com.shop.command.UserLoginCommand;
import com.shop.command.UserSignUpCommand;
import com.shop.config.JWTService;
import com.shop.dto.AuthDto;
import com.shop.dto.UserDtoMapper;
import com.shop.dto.UserListDto;
import com.shop.dto.UserRetrieveDto;
import com.shop.model.User;
import com.shop.repository.*;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import com.shop.shared.enums.ErrorMessagesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService extends BaseService {


    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CommentRepository commentRepository;
    private final ShopCardRepository shopCardRepository;
    private final SavedProductRepository savedProductRepository;
    private final ProductCommentRepository productCommentRepository;
    private final UserProductSearchRepository userProductSearchRepository;

    private final JWTService jwtService;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            JWTService jwtService,
            UserDtoMapper userDtoMapper,
            PasswordEncoder passwordEncoder,

            UserRepository userRepository,
            OrderRepository orderRepository,
            CommentRepository commentRepository,
            ShopCardRepository shopCardRepository,
            SavedProductRepository savedProductRepository,
            ProductCommentRepository productCommentRepository,
            UserProductSearchRepository userProductSearchRepository
    ) {
        this.jwtService = jwtService;
        this.userDtoMapper = userDtoMapper;
        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.commentRepository = commentRepository;
        this.shopCardRepository = shopCardRepository;
        this.savedProductRepository = savedProductRepository;
        this.productCommentRepository = productCommentRepository;
        this.userProductSearchRepository = userProductSearchRepository;
    }

    public ResponseEntity<Response> signUp(UserSignUpCommand command) {
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
    }

    public AuthDto loginAfterSignup(UserLoginCommand command) {
        User userInDB = userRepository.login(command.getEmailOrPhone());
        userRepository.updateLoginCountByUserId(userInDB.getUserId());
        AuthDto authDto = AuthDto.builder().token(generateToken(userInDB)).user(userDtoMapper.apply(userInDB)).build();
        return authDto;
    }

    public ResponseEntity<Response> login(UserLoginCommand command) {
        User userInDB = userRepository.login(command.getEmailOrPhone());
        if (userInDB == null) {
            return badRequestResponse(ErrorMessagesEnum.EMAIL_OR_PHONE_INVALID);
        } else {
            boolean passwordMatches = passwordEncoder.matches(command.getPassword(), userInDB.getPassword());
            if (passwordMatches) {
                userRepository.updateLoginCountByUserId(userInDB.getUserId());
                AuthDto authDto = AuthDto.builder().token(generateToken(userInDB)).user(userDtoMapper.apply(userInDB)).build();
                return successResponse(authDto);
            } else {
                return badRequestResponse(ErrorMessagesEnum.PASSWORD_INVALID);
            }
        }
    }

    private String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getUserId());
        return jwtService.generateToken(user, extraClaims);
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
        return successResponse(new UserListDto(userRepository.getAll()));
    }

    public ResponseEntity<Response> getById() {
        return successResponse(new UserRetrieveDto(userRepository.findByUserId(UserThread.getUserId()).get()));
    }

    @Transactional
    public ResponseEntity<Response> deleteById(Long userId) {
        orderRepository.deleteByUserId(userId);
        shopCardRepository.deleteByUserId(userId);
        userProductSearchRepository.deleteByUserId(userId);
        savedProductRepository.deleteByUserId(userId);
        productCommentRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
        commentRepository.deleteByUserId(userId);
        return successResponse();
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
