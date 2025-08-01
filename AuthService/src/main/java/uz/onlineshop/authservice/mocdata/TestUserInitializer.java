package uz.onlineshop.authservice.mocdata;

import entity.authEntity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import enums.authEnums.Role;
import repository.authRepository.UserRepository;


@Configuration
@Slf4j
public class TestUserInitializer {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner testUserRunner(UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsByEmail("test@gmail.com")) {
                User user = new User();
                user.setEmail("test@gmail.com");
                user.setActive(true);
                user.setPhoneNumber("123456789");
                user.setPassword(passwordEncoder.encode("test123"));
                user.setRole(Role.ADMIN);
                userRepository.save(user);
                log.info("✅ Тестовый пользователь создан: test / test123 / +1234567890");
            } else {
                log.info("⚠️ Тестовый пользователь уже существует:  test / test123 / +1234567890");
            }
        };
    }
}
