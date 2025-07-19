package uz.onlineshop.dtoes.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyRequest {
    @NotBlank(message = "Email kiritilishi kerak!")
    private String email;

    @NotBlank(message = "Kod kiritilishi kerak!")
    private String code;

}
