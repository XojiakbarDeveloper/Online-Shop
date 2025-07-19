package uz.onlineshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.onlineshop.entity.base.TimeLong;
import uz.onlineshop.enums.Role;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class User extends TimeLong {

    private String password;

    private String phoneNumber;

    private String email;

    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

}
