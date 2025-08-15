package uz.onlineshop.orderservice.config;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMessage(String message) {
        System.out.println("📩 OrderService xabar oldi: " + message);
        // Bu yerda kelgan xabarni ishlov berish logikasi bo‘ladi
    }
}
