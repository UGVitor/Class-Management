package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.service.LoginService;
import com.kvy.demogerenciamentoaulas.web.dto.LoginCreateDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.LoginSenhaDto;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.LoginMapper;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Logins", description = "Contém todas as operações relativas aos recursos de CRUD de login.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/logins")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
                @ApiResponse(responseCode = "409", description = "Login e-mail já cadastrado no sistema",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<LoginResponseDto> create(@Valid @RequestBody LoginCreateDTO createDTO) {
        Login user = loginService.salvar(LoginMapper.toLogin(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(LoginMapper.toDto(user));
    }

    @Operation(summary = "Recuperar um usuário pelo id", description = "Recuperar um usuário pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<LoginResponseDto> getById(@PathVariable Long id) {
        Login user = loginService.buscarPorId(id);
        return ResponseEntity.ok(LoginMapper.toDto(user));
    }

    @Operation(summary = "Atualizar senha", description = "Atualizar senha",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody LoginSenhaDto dto) {
        Login user = loginService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar login", description = "Atualiza um login existente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Login> updateLogin(@PathVariable Long id, @RequestBody Login login) {
        Login updatedLogin = loginService.editar(id, login);
        return ResponseEntity.ok(updatedLogin);
    }

    @Operation(summary = "Excluir login", description = "Excluir login pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Login excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable Long id) {
        loginService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os logins", description = "Retorna uma lista de todos os logins",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de logins recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhum login encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<LoginResponseDto>> getLoginAll() {
        List<Login> logins = loginService.buscarTodos();
        return ResponseEntity.ok(LoginMapper.toListDto(logins));
    }

}
