package uz.onlineshop.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "entity.authEntity",
        "entity.productEntity"
})
@EnableJpaRepositories(basePackages = {
        "repository.authRepository",
        "repository.productRepository"
})
@ComponentScan(basePackages = {
        "uz.onlineshop.productservice",
        "uz.onlineshop.productservice.service",
        "uz.onlineshop.productservice.controller"
})
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}