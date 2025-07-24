package uz.onlineshop.orderservice.config;//package uz.onlineshop.productservice.config;
//
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@Service
//public class JwtService {
//
//    private final String secretKey = "0E0864C3B7F5E51220DAE4C25DF9A3AC3F7BD65ABE229C2BA5DF7908776EB56C";
//
//    public String extractUsername(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean isTokenValid(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
//                    .parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//    public List<String> extractRoles(String token) {
//        var claims = Jwts.parser()
//                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
//                .parseClaimsJws(token)
//                .getBody();
//
//        String role = claims.get("role", String.class);
//        return List.of(role); // stringni listga o‘rab qaytaramiz
//    }
//
//
//}
