package uz.onlineshop.dtoes.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "telefon nomer kiritilmadi")
    private String phoneNumber;

    @NotBlank(message = "Email kiritilmadi!")
    private String email;

    @NotBlank(message = "Parol kiritilmadi!")
    private String password;

}
