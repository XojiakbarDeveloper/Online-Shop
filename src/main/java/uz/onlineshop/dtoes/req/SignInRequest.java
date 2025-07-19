package uz.onlineshop.dtoes.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SignInRequest {
    @NotBlank(message = "Email kiritilishi kerak!")
    private String email;

    @NotBlank(message = "Parol kiritilishi kerak!")
    private String password;
}
