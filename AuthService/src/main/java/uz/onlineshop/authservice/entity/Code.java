package uz.onlineshop.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verify_codes")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Boolean isUsed;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime expiredAt;
}
