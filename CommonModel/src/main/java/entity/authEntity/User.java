package entity.authEntity;

import entity.base.TimeLong;
import enums.authEnums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class User extends TimeLong {

    private String password;

    private String email;

    private Boolean active;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

//    @Column(nullable = false)
//    private boolean phoneVerified = false;

    @Enumerated(EnumType.STRING)
    private Role role;

}
