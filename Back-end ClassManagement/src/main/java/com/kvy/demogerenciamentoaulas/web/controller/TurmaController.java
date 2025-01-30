package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.repository.Projection.TurmaProjection;
import com.kvy.demogerenciamentoaulas.service.TurmaService;
import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Turma", description = "Contém todas as operações relativas aos recursos de CRUD de Turma.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    @Operation(summary = "Criar uma nova turma", description = "Recurso para criar uma nova turma",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turma.class))) ,
                    @ApiResponse(responseCode = "409", description = "Turma já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Turma> createTurma(@Valid @RequestBody TurmaDTO turmaDTO){
        Turma savedTurma = turmaService.salvar(turmaDTO);
        System.out.println(turmaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTurma);
    }

    @Operation(summary = "Recuperar turma pelo ID", description = "Recurso para recuperar uma turma específica pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Turma recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aula.class))),
                    @ApiResponse(responseCode = "404", description = "Turma não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Turma> getTurmaById(@PathVariable Long id) {
        Turma turma= turmaService.buscarPorId(id);
        return ResponseEntity.ok(turma);
    }

    @Operation(summary = "Listar todas as Turmas", description = "Recurso para listar todas as Turmas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Turmas recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AulaDTO.class)))
            })
    @GetMapping
    public List<TurmaProjection> getAllTurmas() {
        return turmaService.buscarTodasTurmas();
    }

    @Operation(summary = "Atualizar uma turma existente", description = "Recurso para atualizar uma turma existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turma.class))),
                    @ApiResponse(responseCode = "404", description = "Turma não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Turma> updateTurma(@PathVariable Long id,@Valid @RequestBody TurmaDTO turmaDTO) {
        try {
            Turma updatedTurma = turmaService.editar(id, turmaDTO);
            return ResponseEntity.ok(updatedTurma);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }


    @Operation(summary = "Excluir turma", description = "Recurso para excluir uma turma pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Turma excluída com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        turmaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

