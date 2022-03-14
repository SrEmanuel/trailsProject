package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService{
    @Value("${trails.normal.url}")
    private String ip;

    @Value("${default.mail.sender}")
    private String sender;


    @Override
    public void sendNewTokenEmail(User user, String token){
        SimpleMailMessage sm = prepareNewPasswordEmail(user, token);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(User user, String token) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(user.getEmail());
        sm.setFrom("Projeto Trilhas <"+ sender +">");
        sm.setSubject("Link de recuperação da sua senha!");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Aqui está o seu token: " + ip +"/nova-senha?token="+ token);
        return sm;
    }
}
