package kg.neobis.fms.services.impl;

import kg.neobis.fms.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendEmailToConfirmEmail(String email, String userName) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setSubject("Подтверждение почты! FMS TEAM#1");
        simpleMailMessage.setText("Здравствуйте," + userName + "!\nДля подтверждение почты пожалуйста перейдите по этой ссылке: здесь будет ссылка:)");
        javaMailSender.send(simpleMailMessage);
    }
}
