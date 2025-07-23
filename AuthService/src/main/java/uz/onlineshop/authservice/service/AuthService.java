package uz.onlineshop.authservice.service;

import org.springframework.stereotype.Service;
import uz.onlineshop.authservice.config.payload.base.ResBaseMsg;
import uz.onlineshop.authservice.req.*;
import uz.onlineshop.authservice.res.AuthResponse;
import uz.onlineshop.authservice.res.LoginResponse;



@Service
public interface AuthService {

    ResBaseMsg signUp(SignUpRequest request);

    LoginResponse signIn(SignInRequest request);

    ResBaseMsg forgotPassword(SendCodeRequest request);

    LoginResponse verify(VerifyRequest request);

    ResBaseMsg checkForgotPassword(ForgotPasswordRequest request);

    ResBaseMsg resendCode(SendCodeRequest request);

//    AuthResponse registerByPhone(PhoneRegisterRequest request);
//
//    AuthResponse loginByPhone(PhoneLoginRequest request);


}
