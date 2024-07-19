package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.jwt.JwtToken;
import com.kvy.demogerenciamentoaulas.jwt.JwtUserDetailsService;
import com.kvy.demogerenciamentoaulas.web.dto.UsuarioLoginDto;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request){
        log.info("Processo de Autenticação pelo login {}", dto.getLogin());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(dto.getLogin());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex){
            log.warn("Bad Credentials from username '{}'", dto.getLogin());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}
