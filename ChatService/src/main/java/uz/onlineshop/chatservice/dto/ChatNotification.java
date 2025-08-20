// dto/ChatNotification.java
package uz.onlineshop.chatservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatNotification {
    private String senderId;
    private String content;
}
