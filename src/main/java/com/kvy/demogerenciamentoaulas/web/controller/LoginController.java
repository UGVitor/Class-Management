package com.kvy.demogerenciamentoaulas.web.controller;

<<<<<<< HEAD
import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import com.kvy.demogerenciamentoaulas.service.LoginService;
import com.kvy.demogerenciamentoaulas.web.dto.LoginCreateDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.LoginSenhaDto;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.LoginMapper;
import jakarta.validation.Valid;
=======
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import com.kvy.demogerenciamentoaulas.service.LoginService;
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/logins")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
<<<<<<< HEAD
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
=======
    public ResponseEntity<Login> create(@RequestBody Login login) {
        Login user = loginService.salvar(login);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Login> getById(@PathVariable Long id) {
        Login user = loginService.buscarPorId(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Login> updatePassaword(@PathVariable Long id, @RequestBody Login usuario) {
        Login user = loginService.editarSenha(id, usuario.getPassword());
        return ResponseEntity.ok(user);
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
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
<<<<<<< HEAD

    @GetMapping
    public ResponseEntity<List<LoginResponseDto>> getLoginAll() {
        List<Login> logins = loginService.buscarTodos();
        return ResponseEntity.ok(LoginMapper.toListDto(logins));
    }

=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
}
