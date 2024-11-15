package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.repository.Projection.LoginProjection;
import com.kvy.demogerenciamentoaulas.service.LoginService;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginSenhaDTO;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Logins", description = "Contém todas as operações relativas aos recursos de CRUD de login.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/logins")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "Criar um novo login", description = "Recurso para criar um novo login.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Login criado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))),
                    @ApiResponse(responseCode = "409", description = "Login já cadastrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<LoginDTO> create(@RequestBody LoginDTO loginDTO) {
        Login login = loginService.convertToEntity(loginDTO);
        Login savedLogin  = loginService.salvar(login);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginService.convertToDTO(savedLogin));
    }

    @Operation(summary = "Recuperar login por ID", description = "Recurso para recuperar um login pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login recuperado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))),
                    @ApiResponse(responseCode = "404", description = "Login não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<LoginDTO> getById(@PathVariable Long id) {
        Login login = loginService.buscarPorId(id);
        return ResponseEntity.ok(loginService.convertToDTO(login));
    }

    @Operation(summary = "Atualizar login", description = "Recurso para atualizar as informações de um login.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login atualizado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))),
                    @ApiResponse(responseCode = "404", description = "Login não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<LoginDTO> updateLogin(@PathVariable Long id, @RequestBody LoginDTO loginDTO) {
        Login login = loginService.convertToEntity(loginDTO);
        Login updatedLogin = loginService.editar(id, login);
        return ResponseEntity.ok(loginService.convertToDTO(updatedLogin));
    }

    @Operation(summary = "Atualizar senha de login", description = "Recurso para atualizar a senha de um login.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))),
                    @ApiResponse(responseCode = "404", description = "Login não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Erro de validação de senha.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}/senha")
    public ResponseEntity<LoginDTO> updatePassword(@PathVariable Long id, @RequestBody LoginSenhaDTO loginSenhaDTO) {
        Login updatedLogin = loginService.editarSenha(id, loginSenhaDTO);
        return ResponseEntity.ok(loginService.convertToDTO(updatedLogin));
    }

    @Operation(summary = "Excluir login por ID", description = "Recurso para excluir um login pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Login excluído com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Login não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable Long id) {
        loginService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os logins", description = "Recurso para listar todos os logins cadastrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Logins listados com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class)))
            })
    @GetMapping
    public ResponseEntity<List<LoginProjection>> getAllLogins() {
        List<LoginProjection> logins = loginService.buscarTodos();
        return ResponseEntity.ok(logins);
    }
}
