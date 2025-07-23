//package uz.onlineshop.authservice.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.Random;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//@RequiredArgsConstructor
//public class VerificationService {
//
//    private final SmsService smsService;
//    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
//    private final Map<String, Integer> attemptCounts = new ConcurrentHashMap<>();
//
//    private static final int MAX_ATTEMPTS = 3;
//    private static final long CODE_EXPIRATION_MINUTES = 5;
//
//    public void sendVerificationCode(String phoneNumber) {
//        // 1. Kod generatsiya qilish
//        String code = String.format("%06d", new Random().nextInt(999999));
//
//        // 2. Cachelash
//        verificationCodes.put(phoneNumber, code);
//        attemptCounts.put(phoneNumber, 0);
//
//        // 3. SMS jo'natish
//        String message = String.format("Tasdiqlash kodingiz: %s. Kod %d daqiqa amal qiladi.", code, CODE_EXPIRATION_MINUTES);
//        smsService.sendSms(phoneNumber, message);
//    }
//
//    public boolean verifyCode(String phoneNumber, String code) {
//        // 1. Urinishlar sonini tekshirish
//        Integer attempts = attemptCounts.getOrDefault(phoneNumber, 0);
//        if (attempts >= MAX_ATTEMPTS) {
//            throw new RuntimeException("Too many attempts. Please request a new code");
//        }
//
//        // 2. Kodni tekshirish
//        String savedCode = verificationCodes.get(phoneNumber);
//        if (savedCode == null) {
//            throw new RuntimeException("Verification code expired or not found");
//        }
//
//        boolean isValid = savedCode.equals(code);
//        if (!isValid) {
//            attemptCounts.put(phoneNumber, attempts + 1);
//        } else {
//            // Muvaffaqiyatli tasdiqlanganda cachelarni tozalash
//            verificationCodes.remove(phoneNumber);
//            attemptCounts.remove(phoneNumber);
//        }
//
//        return isValid;
//    }
//}