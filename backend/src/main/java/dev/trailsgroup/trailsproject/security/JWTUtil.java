package dev.trailsgroup.trailsproject.security;

import dev.trailsgroup.trailsproject.services.RefreshTokenService;
import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Lazy
    @Autowired
    RefreshTokenService refreshTokenService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.authentication}")
    private Long expirationAuthentication;

    @Value("${jwt.expiration.refresh}")
    private Long expirationRefresh;

    public String generateAuthenticationToken(String email){
        return generateToken(email, expirationAuthentication);
    }

    public String generateRefreshToken(String email){
        String token = generateToken(email, expirationRefresh);
        refreshTokenService.saveRefreshToken(email, token);
        return token;
    }

    protected String generateToken(String email, Long expiration){
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public void verifyJwtToken(String token) {
        if (!isValid(token.substring(7))) {
            throw new AuthorizationException("Token inválido!");
        }
    }

    public boolean isValid(String token){
        Claims claims = getClaims(token);
        if(claims != null){
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if(username != null && expiration != null && now.before(expiration)){
                return true;
            }
        }
        return false;
    }

    public String getUserName(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }




}
