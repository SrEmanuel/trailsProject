package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.mail.SimpleMailMessage;

import jakarta.mail.internet.MimeMessage;

public interface EmailService {

    void sendEmail(SimpleMailMessage msg);

    void sendNewTokenEmail(User user, String token);

    void sendHtmlEmail(MimeMessage msg);
}
