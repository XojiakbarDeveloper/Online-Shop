package uz.onlineshop.authservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import uz.onlineshop.authservice.config.application.exceptions.RestException;
import uz.onlineshop.authservice.entity.Code;
import uz.onlineshop.authservice.repository.CodeRepository;
import uz.onlineshop.commonmodel.enums.ErrorTypeEnum;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
@EnableAsync
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;
    private final MailService mailService;
    private final Random random = new Random();

    public void sendCode(String email) {
        Optional<Code> foundCode = codeRepository.findByEmail(email);
        String generatedCode = String.valueOf(random.nextInt(900000) + 100000);

        Code code;
        int CODE_EXPIRATION = 5;
        if (foundCode.isPresent()) {
            //if user already exists
            code = foundCode.get();

            code.setCode(generatedCode);
            code.setIsUsed(Boolean.FALSE);
            code.setExpiredAt(LocalDateTime.now().plusMinutes(CODE_EXPIRATION));
        } else {
            //when new user registered account
            code = Code.builder()
                    .email(email)
                    .code(generatedCode)
                    .isUsed(Boolean.FALSE)
                    .expiredAt(LocalDateTime.now().plusMinutes(CODE_EXPIRATION))
                    .build();
        }
        codeRepository.save(code);

        //generated code send to given email
        String body = String.format("<p class=\"code\">%s</p>", generatedCode);
        mailService.sendCode(
                email,
                body,
                "Kodning amal qilish muddati 5 minut.",
                "Ro'yxatdan o'tish uchun tasdiqlash kodi"
        );
    }

    public Boolean checkCode(String email, String inputCode) {
        Code code = codeRepository.findByEmail(email)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CODE_NOT_FOUND));

        if (code.getCode().equals(inputCode)) {
            if (LocalDateTime.now().isAfter(code.getExpiredAt())) {
                throw RestException.restThrow(ErrorTypeEnum.CODE_EXPIRED);
            }

            if (code.getIsUsed()) {
                throw RestException.restThrow(ErrorTypeEnum.CODE_IS_USED);
            }

            code.setExpiredAt(LocalDateTime.now());
            code.setIsUsed(Boolean.TRUE);
            codeRepository.save(code);

            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
