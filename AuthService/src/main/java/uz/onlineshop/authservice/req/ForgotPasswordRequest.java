package uz.onlineshop.authservice.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "Email kiritilishi kerak!")
    private String email;

    @NotBlank(message = "Yangi parol kiritilishi kerak!")
    private String password;

    @NotBlank(message = "Kod kiritilmadi!")
    private String code;
}