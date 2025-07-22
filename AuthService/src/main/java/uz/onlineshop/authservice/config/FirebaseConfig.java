package uz.onlineshop.authservice.config;//package uz.onlineshop.config;
//
//
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Service
//public class FirebaseConfig {
//
//    @PostConstruct
//    public void init() throws IOException {
//        InputStream serviceAccount = new ClassPathResource("firebase/LMS-University-firebase-admin-sdk.json").getInputStream();
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//        }
//    }
//}
