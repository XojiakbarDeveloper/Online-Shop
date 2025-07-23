
package uz.onlineshop.authservice.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PhoneRegisterRequest {
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^998[0-9]{9}$", message = "Phone number must start with 998 and contain 12 digits")
    private String phoneNumber;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Verification code cannot be blank")
    @Size(min = 6, max = 6, message = "Verification code must be 6 digits")
    private String code;

    private String password;
}
