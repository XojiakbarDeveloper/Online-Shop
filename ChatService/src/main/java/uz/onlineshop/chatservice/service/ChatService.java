// service/ChatService.java
package uz.onlineshop.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.onlineshop.chatservice.dto.ChatMessage;
import uz.onlineshop.chatservice.entity.MessageEntity;
import uz.onlineshop.chatservice.repository.MessageRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;

    public MessageEntity saveMessage(ChatMessage chatMessage) {
        MessageEntity message = MessageEntity.builder()
                .senderId(chatMessage.getSenderId())
                .receiverId(chatMessage.getReceiverId())
                .content(chatMessage.getContent())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        return messageRepository.save(message);
    }
}
