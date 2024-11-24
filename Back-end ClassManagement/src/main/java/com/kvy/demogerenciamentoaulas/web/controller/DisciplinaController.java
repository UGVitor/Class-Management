package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.repository.Projection.DisciplinaProjection;
import com.kvy.demogerenciamentoaulas.service.DisciplinaService;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaDTO;
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

@Tag(name = "Disciplinas", description = "Contém todas as operações relativas aos recursos de CRUD de disciplina.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @Operation(summary = "Criar uma nova disciplina", description = "Recurso para criar uma nova disciplina",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Disciplina.class))),
                    @ApiResponse(responseCode = "409", description = "Disciplina já cadastrada no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Disciplina> createDisciplina(@Valid @RequestBody DisciplinaDTO disciplinaDTO) {
        Disciplina savedDisciplina = disciplinaService.salvar(disciplinaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDisciplina);
    }

    @Operation(summary = "Recuperar uma disciplina pelo ID", description = "Recurso para recuperar uma disciplina específica pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Disciplina.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> getDisciplinaById(@PathVariable Long id) {
        Disciplina disciplina = disciplinaService.buscarPorId(id);
        return ResponseEntity.ok(disciplina);
    }

    @Operation(summary = "Atualizar uma disciplina", description = "Recurso para atualizar os dados de uma disciplina existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Disciplina.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> updateDisciplina(@PathVariable Long id, @Valid @RequestBody DisciplinaDTO disciplinaDTO) {
        Disciplina updatedDisciplina = disciplinaService.editar(id, disciplinaDTO);
        return ResponseEntity.ok(updatedDisciplina);
    }

    @Operation(summary = "Excluir uma disciplina", description = "Recurso para excluir uma disciplina pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Disciplina excluída com sucesso",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        disciplinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todas as disciplinas", description = "Recurso para listar todas as disciplinas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de recursos recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisciplinaProjection.class)))
            })
    @GetMapping
    public ResponseEntity<List<DisciplinaProjection>> getDisciplinaAll() {
        List<DisciplinaProjection> disciplinas = disciplinaService.buscarTodos();
        return ResponseEntity.ok(disciplinas);
    }
}
