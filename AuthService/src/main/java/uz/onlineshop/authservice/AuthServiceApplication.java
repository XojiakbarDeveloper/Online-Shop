package uz.onlineshop.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "uz.onlineshop.authservice",
        "repository.authRepository",
        "entity.authEntity"
})
@EnableJpaRepositories(basePackages = {
        "repository.authRepository",
        "uz.onlineshop.authservice.repository"
})
@EntityScan(basePackages = "entity.authEntity")
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}