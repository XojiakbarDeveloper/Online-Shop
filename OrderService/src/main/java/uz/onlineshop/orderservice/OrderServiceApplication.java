package uz.onlineshop.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "uz.onlineshop.orderservice",
        "repository.authRepository",
        "entity.authEntity"
})
@EnableJpaRepositories(basePackages = {
        "repository.authRepository",
        "repository.orderRepository",
        "repository.productRepository"
})
@EntityScan(basePackages = {
        "entity.authEntity",
        "entity.orderEntity",
        "entity.productEntity",
})
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}