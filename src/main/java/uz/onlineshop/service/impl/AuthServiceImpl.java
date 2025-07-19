package uz.onlineshop.service.impl;


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
import uz.onlineshop.config.UserPrincipal;
import uz.onlineshop.config.application.exceptions.RestException;
import uz.onlineshop.config.jwt.JwtTokenProvider;
import uz.onlineshop.config.payload.base.ResBaseMsg;
import uz.onlineshop.dtoes.req.*;
import uz.onlineshop.entity.User;
import uz.onlineshop.enums.ErrorTypeEnum;
import uz.onlineshop.enums.Role;
import uz.onlineshop.mapper.UserMapper;
import uz.onlineshop.repository.UserRepository;
import uz.onlineshop.service.AuthService;
import uz.onlineshop.service.CodeService;

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

    @Override
    public ResBaseMsg signUp(SignUpRequest request) {
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw RestException.restThrow(ErrorTypeEnum.INVALID_EMAIL);
        }

        Role roles = Role.USER;

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


//    public LoginResponse googleSign(String uid) {
//        try {
//            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
//            Optional<User> optionalUser = userRepository.findByEmail(userRecord.getEmail());
//
//            User user;
//            if (optionalUser.isPresent()) {
//                user = optionalUser.get();
//            } else {
//                user = User.builder()
//                        .email(userRecord.getEmail())
//                        .active(Boolean.TRUE)
//                        .role(Role.USER)
//                        .build();
//                userRepository.save(user);
//            }
//
//            log.info("User sign in with Google Auth: {}", userRecord.getEmail());
//
//            String token = jwtTokenProvider.generateToken(user);
//            return UserMapper.toLoginResponse(user, token);
//        } catch (FirebaseAuthException e) {
//            e.printStackTrace();
//            throw RestException.restThrow(ErrorTypeEnum.GOOGLE_AUTH_ERROR);
//        }
//    }

}




