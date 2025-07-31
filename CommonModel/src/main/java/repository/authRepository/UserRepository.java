package repository.authRepository;


import entity.authEntity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndActive(String email, boolean active);

    Optional<User> findByEmail(@NotBlank(message = "Email kiritilmadi!") String email);

    boolean existsByEmail(String mail);

    Optional<Object> findByPhoneNumber(@NotBlank(message = "Phone number cannot be blank") @Pattern(regexp = "^998[0-9]{9}$", message = "Phone number must start with 998 and contain 12 digits") String phoneNumber);

    boolean existsByPhoneNumber(@NotBlank(message = "Phone number cannot be blank") @Pattern(regexp = "^998[0-9]{9}$", message = "Phone number must start with 998 and contain 12 digits") String phoneNumber);
}


