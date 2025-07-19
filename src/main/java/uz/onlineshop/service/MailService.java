package uz.onlineshop.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.onlineshop.dtoes.MailDto;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender sender;

    @Async
    public void sendCode(String email, String body, String title, String subject) {
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 50px auto;
                            background-color: #ffffff;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                        }
                        .header {
                            background-color: #4B9CD3;
                            color: white;
                            text-align: center;
                            padding: 20px;
                        }
                        .content {
                            padding: 30px;
                            text-align: center;
                        }
                        .footer {
                            background-color: #f1f1f1;
                            color: #777;
                            text-align: center;
                            padding: 15px;
                            font-size: 12px;
                        }
                        a {
                            color: #4CAF50;
                            text-decoration: none;
                        }
                        .code {
                            font-size: 24px;
                            font-weight: bold;
                            color: #333;
                            background-color: #f1f1f1;
                            padding: 10px 20px;
                            border-radius: 4px;
                            margin: 20px auto;
                            display: inline-block;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h2>%s</h2>
                        </div>
                        <div class="content">
                         %s
                        </div>
                        <div class="footer">
                            <p><b>© 2025 LMS.uz</b></p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(title, body);

        MailDto dto = new MailDto("LMS.uz", email, subject, htmlContent, true);
        try {
            send(dto);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(MailDto dto) throws MessagingException {
        log.info("Sending email to {}", dto.getTo());

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(dto.getTo());
        mimeMessageHelper.setSubject(dto.getSubject());
        mimeMessageHelper.setText(dto.getBody(), dto.isHtml());

        sender.send(mimeMessage);

        log.info("Email sent to {}", dto.getTo());
    }
}
