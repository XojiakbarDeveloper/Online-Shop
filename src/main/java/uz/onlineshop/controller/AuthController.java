package uz.onlineshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.onlineshop.config.payload.base.ApiResult;
import uz.onlineshop.config.payload.base.ResBaseMsg;
import uz.onlineshop.dtoes.req.*;
import uz.onlineshop.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResult<ResBaseMsg> signUp(@RequestBody @Valid SignUpRequest request) {
        return ApiResult.successResponse(authService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ApiResult<LoginResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ApiResult.successResponse(authService.signIn(request));
    }

    @PostMapping("/verify-account")
    public ApiResult<LoginResponse> verify(@RequestBody @Valid VerifyRequest request) {
        return ApiResult.successResponse(authService.verify(request));
    }

    @PostMapping("/resend-code")
    public ApiResult<ResBaseMsg> resendCode(@RequestBody @Valid SendCodeRequest request) {
        return ApiResult.successResponse(authService.resendCode(request));
    }

    @PostMapping("/forgot-password")
    public ApiResult<ResBaseMsg> forgotPassword(@RequestBody @Valid SendCodeRequest request) {
        return ApiResult.successResponse(authService.forgotPassword(request));
    }

    @PostMapping("/check-forgot-password")
    public ApiResult<ResBaseMsg> checkForgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        return ApiResult.successResponse(authService.checkForgotPassword(request));
    }


}
