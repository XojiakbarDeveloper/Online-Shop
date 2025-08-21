// dto/ChatMessage.java
package uz.onlineshop.chatservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String senderId;     // kim yozdi (mijoz yoki admin id)
    private String receiverId;   // kimga yuborildi
    private String content;      // xabar matni
    private String timestamp;    // vaqt
}
