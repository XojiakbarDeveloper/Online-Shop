package uz.onlineshop.service;

import org.springframework.stereotype.Service;
import uz.onlineshop.config.payload.base.ResBaseMsg;
import uz.onlineshop.dtoes.req.*;


@Service
public interface AuthService {

    ResBaseMsg signUp(SignUpRequest request);

    LoginResponse signIn(SignInRequest request);

    ResBaseMsg forgotPassword(SendCodeRequest request);

    LoginResponse verify(VerifyRequest request);

    ResBaseMsg checkForgotPassword(ForgotPasswordRequest request);

    ResBaseMsg resendCode(SendCodeRequest request);


}
