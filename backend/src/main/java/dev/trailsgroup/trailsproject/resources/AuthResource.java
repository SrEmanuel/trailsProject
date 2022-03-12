package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.EmailDTO;
import dev.trailsgroup.trailsproject.dto.PasswordDTO;
import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.security.JWTUtil;
import dev.trailsgroup.trailsproject.security.UserSS;
import dev.trailsgroup.trailsproject.services.AuthService;
import dev.trailsgroup.trailsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value= "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response){
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer "+ token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> refreshToken(@Valid @RequestBody EmailDTO email){
        service.sendNewToken(email.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<User> changePassword(@Valid @RequestBody PasswordDTO password, @RequestParam(name = "token") String token){
        User user = service.changePassword(token, password);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/change-password")
                .buildAndExpand(user).toUri();
        return ResponseEntity.created(uri).body(user);
    }






}
