package kg.neobis.fms.services;

public interface EmailSenderService {
    void sendEmailToConfirmEmail(String email, String userName);
}