package uz.onlineshop.chatservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.onlineshop.chatservice.entity.MessageEntity;


public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}