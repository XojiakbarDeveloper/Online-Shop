// controller/ChatController.java
package uz.onlineshop.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import uz.onlineshop.chatservice.dto.ChatMessage;
import uz.onlineshop.chatservice.service.ChatService;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.send")       // Clientdan xabar kelganda
    @SendTo("/topic/messages")          // Barcha ulanishlarga jo‘natiladi
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatService.saveMessage(chatMessage);
        return chatMessage;
    }
}
