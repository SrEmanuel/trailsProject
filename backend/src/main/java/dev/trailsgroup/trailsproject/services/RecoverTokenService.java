package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.PasswordDTO;
import dev.trailsgroup.trailsproject.entities.RecoverToken;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.repositories.RecoverTokenRepository;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

@Service
public class RecoverTokenService {

    @Autowired
    private UserService userService;

    @Autowired
    private RecoverTokenRepository recoverTokenRepository;

    @Autowired
    private Pbkdf2PasswordEncoder pe;

    @Autowired
    private EmailService emailService;

    @Value("${recover-token.expiration}")
    private Long expiration;



    public void sendNewRecoverToken(String email){
        User user = userService.findByEmail(email);
        try {
            String rawUUID = UUID.randomUUID().toString().replace("-", "");
            recoverTokenRepository.save(new RecoverToken(null, rawUUID, user, expiration));

            String token = email + "-ft-" + rawUUID;
            String secureToken = Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));

            emailService.sendNewTokenEmail(user, secureToken);
        }catch (DataIntegrityViolationException e){
            RecoverToken rt = recoverTokenRepository.findByUser(user);
            if(Instant.now().isAfter(rt.getExpirationDate())){
                recoverTokenRepository.delete(rt);
                sendNewRecoverToken(email);
            }
            ZonedDateTime instant = Instant.ofEpochMilli((rt.getExpirationDate().toEpochMilli() - Instant.now().toEpochMilli())).atZone(ZoneId.of("UTC-3"));
            String counter;
            int minute = instant.getMinute();
            int second = instant.getSecond();
            if(minute > 0)
                counter = minute +" minutos e "+second +" segundos";
            else
                counter = second +" segundos";
            throw new AuthorizationException("Já existe um pedido de recuperação de senha para essa conta! Você poderá pedir outro token em: "
            + counter);
        }

    }

    public void changePasswordByRecoverToken(String encodedToken, PasswordDTO pa){
        try {
            String[] rawToken = decodeRecoverToken(encodedToken);
            User user = userService.findByEmail(rawToken[0]);

            if (pa.getPassword().equals(pa.getConfirmPassword())) {
                if (validateRecoverToken(rawToken[1], user)) {
                    user.setPassword(pe.encode(pa.getPassword()));
                    userService.save(user);
                }else{
                    throw new AuthorizationException("Token inválido! Reinicie o processo de recuperação de senha.");
                }
            }else{
                throw new AuthorizationException("As senhas informadas NÃO são iguais.");
            }

        }catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e){
            throw new AuthorizationException("Token não reconhecido!");
        }
    }

    protected boolean validateRecoverToken(String tokenS,  User user){
        RecoverToken recoverToken = recoverTokenRepository.findByUser(user);

        if(recoverToken != null && tokenS.equals(recoverToken.getRecoverToken())){
            if(Instant.now().isBefore(recoverToken.getExpirationDate())){
                recoverTokenRepository.delete(recoverToken);
                return true;
            }
            else{
                recoverTokenRepository.delete(recoverToken);
                return false;
            }
        }else{
            return false;
        }
    }

    protected String[] decodeRecoverToken(String token){
        String decodedToken = new String(Base64.getDecoder().decode(token));
        return decodedToken.split("-ft-");
    }
}
