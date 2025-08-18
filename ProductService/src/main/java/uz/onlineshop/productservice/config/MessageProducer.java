//package uz.onlineshop.productservice.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//import uz.onlineshop.productservice.dtoes.res.ProductResponse;
//
//@Component
//@RequiredArgsConstructor
//public class MessageProducer {
//
//    private final RabbitTemplate rabbitTemplate;
//
//    private static final String EXCHANGE = "productExchange";
//    private static final String ROUTING_KEY = "productRoutingKey";
//
//    // 🔹 Bu yerda ProductResponse obyektini qabul qilamiz
//    public void sendProductMessage(ProductResponse product) {
//        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, product);
//        System.out.println("📤 RabbitMQ ga xabar yuborildi: " + product);
//    }
//}
