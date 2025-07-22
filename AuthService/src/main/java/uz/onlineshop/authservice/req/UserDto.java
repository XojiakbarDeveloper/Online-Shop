package uz.onlineshop.authservice.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @JsonProperty("id")
    public Long id;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "role")
    private String role;

}
