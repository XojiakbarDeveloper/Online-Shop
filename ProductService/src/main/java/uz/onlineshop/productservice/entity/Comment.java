package uz.onlineshop.productservice.entity;

import entity.base.TimeLong;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends TimeLong {


    private Long productId;
    private Long userId;

    @Column(nullable = false, length = 1000)
    private String text;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
