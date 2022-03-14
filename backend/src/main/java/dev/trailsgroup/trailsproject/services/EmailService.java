package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(SimpleMailMessage msg);

    void sendNewTokenEmail(User user, String newPass);
}
