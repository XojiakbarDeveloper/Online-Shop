package uz.onlineshop.authservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.onlineshop.authservice.config.UserPrincipal;
import uz.onlineshop.authservice.config.application.exceptions.RestException;
import uz.onlineshop.authservice.config.jwt.JwtTokenProvider;
import uz.onlineshop.authservice.config.payload.base.ResBaseMsg;
import uz.onlineshop.authservice.entity.User;

import uz.onlineshop.authservice.mapper.UserMapper;
import uz.onlineshop.authservice.repository.UserRepository;
import uz.onlineshop.authservice.req.*;
import uz.onlineshop.authservice.res.AuthResponse;
import uz.onlineshop.authservice.res.LoginResponse;
import uz.onlineshop.authservice.service.AuthService;
import uz.onlineshop.authservice.service.CodeService;
//import uz.onlineshop.authservice.service.SmsService;
//import uz.onlineshop.authservice.service.VerificationService;
import uz.onlineshop.commonmodel.enums.ErrorTypeEnum;
import uz.onlineshop.commonmodel.enums.Role;


import java.util.Optional;

@Component
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CodeService codeService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
//    private final VerificationService  verificationService;
//    private final SmsService smsService;

    @Override
    public ResBaseMsg signUp(SignUpRequest request) {
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw RestException.restThrow(ErrorTypeEnum.INVALID_EMAIL);
        }

        Role roles = Role.CUSTOMER;

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();

            if (user.getActive()) {
                throw RestException.restThrow(ErrorTypeEnum.EMAIL_ALREADY_EXISTS);
            }

            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(roles);

        } else {

            user = User.builder()
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .active(false)
                    .role(roles)
                    .build();
        }

        userRepository.save(user);


        codeService.sendCode(user.getEmail());

        return new ResBaseMsg("Ro'yxatdan muvaffaqiyatli o'tildi. Emailingizni tasdiqlang.");
    }


    @Override
    public LoginResponse signIn(SignInRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            if (!userPrincipal.user().getActive()) {
                throw RestException.restThrow(ErrorTypeEnum.USER_NOT_ACTIVE);
            }


            String token = jwtTokenProvider.generateToken(userPrincipal.user());

            return UserMapper.toLoginResponse(userPrincipal.user(), token);

        } catch (BadCredentialsException e) {
            throw RestException.restThrow(ErrorTypeEnum.INVALID_EMAIL_OR_PASSWORD);
        }
    }



    public ResBaseMsg forgotPassword(SendCodeRequest request) {
        if (userRepository.findByEmailAndActive(request.getEmail(), true).isEmpty()) {
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND);
        }
        codeService.sendCode(request.getEmail());

        return new ResBaseMsg("Emailingizga kod yuborildi!");
    }




    public LoginResponse verify(VerifyRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        if (user.getActive()) {
            throw RestException.restThrow(ErrorTypeEnum.ALREADY_ACTIVE);
        }


        if (!codeService.checkCode(user.getEmail(), request.getCode())) {
            throw RestException.restThrow(ErrorTypeEnum.CODE_NOT_MATCH);
        }

        user.setActive(Boolean.TRUE);
        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user);

        log.info("New user registered: {}", request.getEmail());
        return UserMapper.toLoginResponse(user, token);
    }



    public ResBaseMsg checkForgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmailAndActive(request.getEmail(), true)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));


        if (!codeService.checkCode(user.getEmail(), request.getCode())) {
            throw RestException.restThrow(ErrorTypeEnum.CODE_NOT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return new ResBaseMsg("Parol muvaffaqqiyatli yangilandi.");
    }



    public ResBaseMsg resendCode(SendCodeRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        if (user.getActive()) {
            throw RestException.restThrow(ErrorTypeEnum.ALREADY_ACTIVE);
        }
        codeService.sendCode(request.getEmail());
        return new ResBaseMsg("Emailingizga kod yuborildi!");
    }


    public User getCurrentUser() throws ChangeSetPersister.NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Foydalanuvchi tizimga kirmagan");
        }

        String email = authentication.getName(); // odatda bu username yoki email bo'ladi

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

//    public AuthResponse registerByPhone(PhoneRegisterRequest request) {
//        // 1. Kodni tekshirish
//        if (!verificationService.verifyCode(request.getPhoneNumber(), request.getCode())) {
//            throw new RuntimeException("Invalid verification code");
//        }
//
//        // 2. User mavjudligini tekshirish
//        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
//            throw new RuntimeException("Phone number already exists");
//        }
//
//        // 3. Yangi user yaratish
//        User user = new User();
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setPassword(request.getPassword());
//        user.setPhoneVerified(true);
//        user.setRole(Role.CUSTOMER);
//
//        User savedUser = userRepository.save(user);
//
//        // 4. Token generatsiya qilish
//        String token = jwtTokenProvider.generateToken(savedUser);
//
//        return AuthResponse.builder()
//                .token(token)
//                .build();
//    }
//
//    public AuthResponse loginByPhone(PhoneLoginRequest request) {
//        // 1. Kodni tekshirish
//        if (!verificationService.verifyCode(request.getPhoneNumber(), request.getCode())) {
//            throw new RuntimeException("Invalid verification code");
//        }
//
//        // 2. User ni topish
//        User user = (User) userRepository.findByPhoneNumber(request.getPhoneNumber())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 3. Token generatsiya qilish
//        String token = jwtTokenProvider.generateToken(user);
//
//        return AuthResponse.builder()
//                .token(token)
//                .build();
//    }


}




