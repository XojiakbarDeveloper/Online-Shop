package uz.onlineshop.authservice.service;

import org.springframework.stereotype.Service;
import uz.onlineshop.authservice.config.payload.base.ResBaseMsg;
import uz.onlineshop.authservice.req.ForgotPasswordRequest;
import uz.onlineshop.authservice.req.VerifyRequest;
import uz.onlineshop.authservice.req.SendCodeRequest;
import uz.onlineshop.authservice.req.SignInRequest;
import uz.onlineshop.authservice.req.SignUpRequest;
import uz.onlineshop.authservice.res.LoginResponse;



@Service
public interface AuthService {

    ResBaseMsg signUp(SignUpRequest request);

    LoginResponse signIn(SignInRequest request);

    ResBaseMsg forgotPassword(SendCodeRequest request);

    LoginResponse verify(VerifyRequest request);

    ResBaseMsg checkForgotPassword(ForgotPasswordRequest request);

    ResBaseMsg resendCode(SendCodeRequest request);


}
