//package uz.onlineshop.orderservice.rebbitMQ;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import uz.onlineshop.productservice.dtoes.res.ProductResponse;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class RabbitMQConfig {
//
//    @Value("${order.rabbit.queue}")
//    private String queueName;
//
//    @Value("${order.rabbit.exchange}")
//    private String exchangeName;
//
//    @Value("${order.rabbit.routing-key}")
//    private String routingKey;
//
//    @Bean
//    public Queue orderProductQueue() {
//        return new Queue(queueName, true);
//    }
//
//    @Bean
//    public TopicExchange productExchange() {
//        return new TopicExchange(exchangeName);
//    }
//
//    @Bean
//    public Binding binding(Queue orderProductQueue, TopicExchange productExchange) {
//        return BindingBuilder.bind(orderProductQueue).to(productExchange).with(routingKey);
//    }
//
//    /**
//     * Jackson converter va class-mapping.
//     * ProductService akka package bilan yuboradi:
//     * "uz.onlineshop.productservice.dtoes.res.ProductResponse"
//     * biz shu nomni OrderService-ning ProductResponse klassiga map qilamiz.
//     */
//    @Bean
//    public MessageConverter jacksonMessageConverter(ObjectMapper objectMapper) {
//        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
//
//        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//
//        Map<String, Class<?>> idClassMapping = new HashMap<>();
//        idClassMapping.put("uz.onlineshop.productservice.dtoes.res.ProductResponse", ProductResponse.class);
//        // Agar ProductService boshqa class nomlarini yuborsa, shu yerga qo'shing
//
//        typeMapper.setIdClassMapping(idClassMapping);
//        // Biz header orqali class mappingni ruxsat etamiz
//        converter.setJavaTypeMapper(typeMapper);
//        return converter;
//    }
//
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
//            ConnectionFactory connectionFactory,
//            MessageConverter messageConverter) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(messageConverter);
//        factory.setConcurrentConsumers(1);
//        factory.setMaxConcurrentConsumers(3);
//        return factory;
//    }
//}
