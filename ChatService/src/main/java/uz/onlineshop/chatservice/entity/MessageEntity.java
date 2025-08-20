package uz.onlineshop.chatservice.entity;

import entity.base.TimeLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity extends TimeLong {
    private String senderId;
    private String receiverId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String timestamp;
}
