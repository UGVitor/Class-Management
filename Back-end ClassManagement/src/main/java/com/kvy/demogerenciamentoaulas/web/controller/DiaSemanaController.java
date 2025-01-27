package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.DiaSemana;
import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.service.DiaSemanaService;
import com.kvy.demogerenciamentoaulas.web.dto.DiaSemanaDTO;
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

@Tag(name = "Dias da Semana", description = "Operações relacionadas ao CRUD de Dia da Semana.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diasSemana")
public class DiaSemanaController {

    private final DiaSemanaService diaSemanaService;

    @Operation(summary = "Criar um novo dia da semana", description = "Recurso para criar um novo dia da semana",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Semestre.class))),
                    @ApiResponse(responseCode = "409", description = "Dia da Semana já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<DiaSemanaDTO> createDiaSemana(@Valid @RequestBody DiaSemanaDTO diaSemanaDTO) {
        DiaSemana savedDiaSemana = diaSemanaService.salvar(diaSemanaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaSemanaService.toDTO(savedDiaSemana));
    }

    @Operation(summary = "Recuperar dia da semana pelo ID", description = "Recurso para recuperar um dia específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dia da semana recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaSemana.class))),
                    @ApiResponse(responseCode = "404", description = "Dia da semana não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<DiaSemanaDTO> getById(@PathVariable Long id) {
        DiaSemana diaSemana = diaSemanaService.buscarPorId(id);
        return ResponseEntity.ok(diaSemanaService.toDTO(diaSemana));
    }

    @Operation(summary = "Excluir dia da semana pelo ID", description = "Recurso para excluir um dia pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Dia da semana excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Dia da semana não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaSemana(@PathVariable Long id) {
        diaSemanaService.excluir(id);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<DiaSemanaDTO> editarDiaSemana(@PathVariable Long id, @Valid @RequestBody DiaSemanaDTO diaSemanaDTO) {
        DiaSemana diaSemanaAtualizado = diaSemanaService.editar(id, diaSemanaDTO);
        return ResponseEntity.ok(diaSemanaService.toDTO(diaSemanaAtualizado));
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
