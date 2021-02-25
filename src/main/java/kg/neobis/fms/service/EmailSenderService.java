package kg.neobis.fms.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String username;

    public void sendEmailToConfirmEmail(String email, String userName){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setSubject("подтверждение почты! FMS TEAM#1");
        simpleMailMessage.setText("Здравствуйте," + userName + "!\nДля подтверждение почты пожалуйста перейдите по этой ссылке: здесь будет ссылка:)");
        javaMailSender.send(simpleMailMessage);
    }



}