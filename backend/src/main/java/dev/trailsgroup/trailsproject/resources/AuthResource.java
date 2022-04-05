package dev.trailsgroup.trailsproject.resources;

import dev.trailsgroup.trailsproject.dto.EmailDTO;
import dev.trailsgroup.trailsproject.dto.PasswordDTO;
import dev.trailsgroup.trailsproject.dto.TokenDTO;
import dev.trailsgroup.trailsproject.resources.utils.StandardMessage;
import dev.trailsgroup.trailsproject.security.JWTUtil;
import dev.trailsgroup.trailsproject.services.RecoverTokenService;
import dev.trailsgroup.trailsproject.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value= "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RecoverTokenService recoverTokenService;

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<Void> refreshToken(@Valid @RequestBody TokenDTO token, HttpServletResponse response){
        response.addHeader("Authorization", "Bearer " + refreshTokenService.generateAccessToken(token.getToken()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/verify-token")
    public ResponseEntity<StandardMessage> verifyToken(@RequestHeader("Authorization") String token){
        jwtUtil.verifyJwtToken(token);
        StandardMessage sm = new StandardMessage(200, "O token informado é valido e está dentro do tempo de expiração!", "/auth/verify-token");
        return ResponseEntity.ok().body(sm);
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<StandardMessage> forgot(@Valid @RequestBody EmailDTO email){
        recoverTokenService.sendNewRecoverToken(email.getEmail());
        StandardMessage sm = new StandardMessage(201, "Token criado e envaido para o email cadastrado com sucesso.", "/auth/forgot");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/forgot")
                .buildAndExpand(sm).toUri();
        return ResponseEntity.created(uri).body(sm);
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<StandardMessage> changePassword(@Valid @RequestBody PasswordDTO password, @RequestParam(name = "token") String token){
        recoverTokenService.changePasswordByRecoverToken(token, password);
        StandardMessage sm =  new StandardMessage(201, "Senha alterada com sucesso!", "/auth/change-password");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/change-password")
                .buildAndExpand(sm).toUri();
        return ResponseEntity.created(uri).body(sm);
    }






}
