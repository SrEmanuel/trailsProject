package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.dto.PasswordDTO;
import dev.trailsgroup.trailsproject.entities.RecoverToken;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.repositories.RecoverTokenRepository;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
import dev.trailsgroup.trailsproject.security.JWTUtil;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecoverTokenRepository recoverTokenRepository;

    @Autowired
    private Pbkdf2PasswordEncoder pe;

    @Autowired
    private JWTUtil jwtUtil;


    @Autowired
    private EmailService emailService;

    @Value("${recover-token.expiration}")
    private Long expiration;



    public void sendNewToken (String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
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
                sendNewToken(email);
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

    public void changePassword(String encodedToken, PasswordDTO pa){
        try {
            String[] rawToken = decodeToken(encodedToken);
            User user = userRepository.findByEmail(rawToken[0]).get();

            if (pa.getPassword().equals(pa.getConfirmPassword())) {
                if (validateRecoverToken(rawToken[1], user)) {
                    user.setPassword(pe.encode(pa.getPassword()));
                    userRepository.save(user);
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

        if(recoverToken != null && tokenS.equals(recoverToken.getAccessToken())){
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

    protected String[] decodeToken(String token){
        String decodedToken = new String(Base64.getDecoder().decode(token));
        return decodedToken.split("-ft-");
    }

    public UserSS verifyJwtToken(String token) {
        if(jwtUtil.isValid(token.substring(7))){
            return UserService.authenticated();
        }else{
            throw new AuthorizationException("Token inválido!");
        }
    }
}
