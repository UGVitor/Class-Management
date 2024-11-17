package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.DiaSemana;
import com.kvy.demogerenciamentoaulas.service.DiaSemanaService;
import com.kvy.demogerenciamentoaulas.web.dto.DiaSemanaDTO;
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

@Tag(name = "Dias da Semana", description = "Operações relacionadas ao CRUD de Dia da Semana.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/diasSemana")
public class DiaSemanaController {

    private final DiaSemanaService diaSemanaService;

    @Operation(summary = "Criar um novo dia da semana", description = "Recurso para criar um novo dia da semana",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaSemana.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<DiaSemana> createDiaSemana(@RequestBody DiaSemanaDTO diaSemanaDTO) {
        try {
            DiaSemana savedDiaSemana = diaSemanaService.salvar(diaSemanaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDiaSemana);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Recuperar dia da semana pelo ID", description = "Recurso para recuperar um dia específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dia da semana recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaSemana.class))),
                    @ApiResponse(responseCode = "404", description = "Dia da semana não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<DiaSemana> getById(@PathVariable Long id) {
        try {
            DiaSemana diaSemana = diaSemanaService.buscarPorId(id);
            return ResponseEntity.ok(diaSemana);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Excluir dia da semana pelo ID", description = "Recurso para excluir um dia pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Dia da semana excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Dia da semana não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaSemana(@PathVariable Long id) {
        try {
            diaSemanaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Editar dia da semana pelo ID", description = "Recurso para editar um dia específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dia da semana atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaSemana.class))),
                    @ApiResponse(responseCode = "404", description = "Dia da semana não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<DiaSemana> editarDiaSemana(@PathVariable Long id, @RequestBody DiaSemanaDTO diaSemanaDTO) {
        try {
            DiaSemana diaSemanaAtualizado = diaSemanaService.editar(id, diaSemanaDTO);
            return ResponseEntity.ok(diaSemanaAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Listar todos os dias da semana", description = "Recurso para listar todos os dias da semana",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de dias recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaSemana.class)))
            })
    @GetMapping
    public ResponseEntity<List<DiaSemana>> getAllDiasSemana() {
        List<DiaSemana> diasSemana = diaSemanaService.buscarTodos();
        return ResponseEntity.ok(diasSemana);
    }
}
