package dev.trailsgroup.trailsproject.resources.exceptions;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private String json() {
        return "{\"status\": 401, "
                + "\"error\": \"Nao autorizado\", "
                + "\"message\": \"Email ou senha invalidos\", "
                + "\"path\": \"/login\"}";
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(json());
    }
}
