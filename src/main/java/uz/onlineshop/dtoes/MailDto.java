package uz.onlineshop.dtoes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String from;
    private String to;
    private String subject;
    private String body;
    private boolean html;
}
