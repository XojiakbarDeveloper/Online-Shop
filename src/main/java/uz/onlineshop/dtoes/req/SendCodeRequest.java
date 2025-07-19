package uz.onlineshop.dtoes.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendCodeRequest {
    @NotBlank(message = "Email kiritilishi kerak!")
    private String email;
}
