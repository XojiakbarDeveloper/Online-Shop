package uz.onlineshop.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.authservice.config.payload.base.ResBaseMsg;
import uz.onlineshop.authservice.req.*;
import uz.onlineshop.authservice.res.AuthResponse;
import uz.onlineshop.authservice.res.LoginResponse;
import uz.onlineshop.authservice.service.AuthService;
//import uz.onlineshop.authservice.service.VerificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
//    private final VerificationService verificationService;


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

//    @PostMapping("/send-code")
//    public ResponseEntity<?> sendVerificationCode(@RequestBody PhoneRequest request) {
//        verificationService.sendVerificationCode(request.getPhoneNumber());
//        return ResponseEntity.ok("Verification code sent");
//    }
//
//    @PostMapping("/verify-code")
//    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeRequest request) {
//        if (verificationService.verifyCode(request.getPhoneNumber(), request.getCode())) {
//            return ResponseEntity.ok("Code verified successfully");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid verification code");
//    }
//
//    @PostMapping("/register-by-phone")
//    public ResponseEntity<AuthResponse> registerByPhone(@RequestBody PhoneRegisterRequest request) {
//        return ResponseEntity.ok(authService.registerByPhone(request));
//    }
//
//    @PostMapping("/login-by-phone")
//    public ResponseEntity<AuthResponse> loginByPhone(@RequestBody PhoneLoginRequest request) {
//        return ResponseEntity.ok(authService.loginByPhone(request));
//    }


}
