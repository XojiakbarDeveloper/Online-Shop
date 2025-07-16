package uz.onlineshop.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.onlineshop.entity.User;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndActive(String email, boolean active);

    Optional<User> findByEmail(@NotBlank(message = "Email kiritilmadi!") String email);

    boolean existsByEmail(String mail);
}


