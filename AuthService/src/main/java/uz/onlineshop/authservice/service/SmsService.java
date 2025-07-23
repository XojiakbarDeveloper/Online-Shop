//package uz.onlineshop.authservice.service;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class SmsService {
//
//    private String token;
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final String email = "YOUR_ESKIZ_EMAIL";
//    private final String password = "YOUR_ESKIZ_PASSWORD";
//
//    @PostConstruct
//    public void init() {
//        refreshToken();
//    }
//
//    public void refreshToken() {
//        String url = "https://notify.eskiz.uz/api/auth/login";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, String> body = new HashMap<>();
//        body.put("email", email);
//        body.put("password", password);
//
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
//            this.token = (String) data.get("token");
//        } else {
//            throw new RuntimeException("Failed to get Eskiz token");
//        }
//    }
//
//    public void sendSms(String phoneNumber, String message) {
//        String url = "https://notify.eskiz.uz/api/message/sms/send";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, String> body = new HashMap<>();
//        body.put("mobile_phone", phoneNumber);
//        body.put("message", message);
//        body.put("from", "4546"); // Eskiz bergan FROM ID
//
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//
//        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//            // Token eskirgan bo'lishi mumkin, yangilaymiz
//            refreshToken();
//            sendSms(phoneNumber, message); // Qayta urinib ko'ramiz
//        } else if (response.getStatusCode() != HttpStatus.OK) {
//            throw new RuntimeException("Error sending SMS: " + response.getBody());
//        }
//    }
//}