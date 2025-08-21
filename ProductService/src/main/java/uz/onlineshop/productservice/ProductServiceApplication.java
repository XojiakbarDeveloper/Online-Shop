package uz.onlineshop.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "entity.authEntity",
        "entity.productEntity",
        "uz.onlineshop.productservice.entity"   // <<-- qo‘shish kerak
})
@EnableJpaRepositories(basePackages = {
        "repository.authRepository",
        "repository.productRepository",
        "uz.onlineshop.productservice.repository" // <<-- qo‘shish kerak
})
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}

