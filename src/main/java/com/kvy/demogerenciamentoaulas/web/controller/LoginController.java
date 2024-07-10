package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import com.kvy.demogerenciamentoaulas.service.LoginService;
import com.kvy.demogerenciamentoaulas.web.dto.LoginCreateDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.LoginSenhaDto;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.LoginMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/logins")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> create(@Valid @RequestBody LoginCreateDTO createDTO) {
        Login user = loginService.salvar(LoginMapper.toLogin(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(LoginMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginResponseDto> getById(@PathVariable Long id) {
        Login user = loginService.buscarPorId(id);
        return ResponseEntity.ok(LoginMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassaword(@PathVariable Long id, @Valid @RequestBody LoginSenhaDto dto) {
        Login user = loginService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Login> updateLogin(@PathVariable Long id, @RequestBody Login login) {
        Login updatedLogin = loginService.editar(id, login);
        return ResponseEntity.ok(updatedLogin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable Long id) {
        loginService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LoginResponseDto>> getLoginAll() {
        List<Login> logins = loginService.buscarTodos();
        return ResponseEntity.ok(LoginMapper.toListDto(logins));
    }

}
