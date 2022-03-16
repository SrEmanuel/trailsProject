package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService{
    @Value("${trails.normal.url}")
    private String ip;

    @Value("${default.mail.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendNewTokenEmail(User user, String token){
        try {
            MimeMessage mm = prepareNewPasswordEmailHTML(user, token);
            sendHtmlEmail(mm);
        }catch (MessagingException | TemplateInputException e ){
            SimpleMailMessage sm = prepareNewPasswordEmail(user, token);
            sendEmail(sm);
        }
    }

    protected SimpleMailMessage prepareNewPasswordEmail(User user, String token) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(user.getEmail());
        sm.setFrom("Projeto Trilhas <"+ sender +">");
        sm.setSubject("Link de recuperação de senha!");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Aqui está o seu token: " + ip +"/nova-senha?token=" + token);
        return sm;
    }

    protected MimeMessage prepareNewPasswordEmailHTML(User user, String token) throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(user.getEmail());
        mmh.setFrom("Projeto Trilhas <"+ sender +">");
        mmh.setSubject("Link de recuperação de senha!");
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(prepareHTML(user, token), true);
        return mimeMessage;
    }

    protected String prepareHTML(User user, String token){
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("token", ip +"/nova-senha?token="+token);
        return templateEngine.process("email/passwordEmail", context);

    }


}
