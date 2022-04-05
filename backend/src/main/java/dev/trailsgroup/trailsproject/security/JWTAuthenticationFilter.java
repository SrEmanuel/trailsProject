package dev.trailsgroup.trailsproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.trailsgroup.trailsproject.dto.CredentialsDTO;
import dev.trailsgroup.trailsproject.resources.exceptions.JWTAuthenticationFailureHandler;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse response) throws AuthenticationException {
        try {
            CredentialsDTO creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CredentialsDTO.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());

            return authenticationManager.authenticate(authToken);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException{

        String username = ((UserSS) auth.getPrincipal()).getUsername();
        UserSS user = (UserSS) auth.getPrincipal();
        String token = jwtUtil.generateAuthenticationToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("RefreshToken", "Bearer " + refreshToken);
        res.addHeader("access-control-expose-headers", "Authorization, authorization");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        res.getWriter().write(ow.writeValueAsString(user));
    }



}
