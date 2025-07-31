package uz.onlineshop.authservice.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorTypeEnum {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server xatoligi!"),
    INVALID_EMAIL_OR_PASSWORD("Email yoki parol xato!"),
    EMAIL_ALREADY_EXISTS("Bunday email allaqachon mavjud!"),
    OLD_PASSWORD_NOT_MATCH("Eski parolingiz xato!"),
    INVALID_REQUEST_PARAMS("Xatoliklar: "),
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Sahifa topilmadi!"),
    METHOD_ERROR("Metod hatoligi!"),
    UNACCEPTABLE("Yuborilgan so'rov turi qabul qilinmadi!"),
    UNSUPPORTED_MEDIA_TYPE("Xato media turi!"),
    INVALID_EMAIL("Email xato!"),
    USER_NOT_ACTIVE("User aktiv emas"),
    CODE_NOT_FOUND("Code topilmadi"),
    CODE_EXPIRED("Code  amal qilish muddati tugagan"),
    CODE_IS_USED("Bu COde ishlatilgan"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Foydalanuvchi topilmadi!"),
    CODE_NOT_MATCH("Kod xato!"),
    ALREADY_ACTIVE("Bu akaunt allaqachon faol!"),
    GOOGLE_AUTH_ERROR("Bunday google akaunt topilmadi!");


    private final String message;
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    ErrorTypeEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    ErrorTypeEnum(String message) {
        this.message = message;
    }
}
