package uz.onlineshop.authservice.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class PhoneRequest {
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^998[0-9]{9}$", message = "Phone number must start with 998 and contain 12 digits")
    private String phoneNumber;
}