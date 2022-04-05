package dev.trailsgroup.trailsproject.services;

import dev.trailsgroup.trailsproject.entities.RecoverToken;
import dev.trailsgroup.trailsproject.entities.RefreshToken;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.repositories.RefreshTokenRepository;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
import dev.trailsgroup.trailsproject.security.JWTUtil;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Value("${jwt.expiration.refresh}")
    private Long expiration;

    @Autowired
    RefreshTokenRepository repository;

    @Lazy
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    public String generateAccessToken(String refreshToken){
        String token = refreshToken.substring(7);
        if(repository.findByRefreshToken(token).isPresent() && jwtUtil.isValid(token)){
            String userName = jwtUtil.getUserName(token);
            return jwtUtil.generateAuthenticationToken(userName);
        }else{
            throw new AuthorizationException("O Token informado NÃO é valido.");
        }
    }

    public void saveRefreshToken(String userEmail, String token){
        repository.save(new RefreshToken(null, token, userRepository.findByEmail(userEmail).get(), expiration));
    }

}
