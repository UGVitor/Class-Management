package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.repository.Projection.AulaProjection;
import com.kvy.demogerenciamentoaulas.service.AulaService;
import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;
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

@Tag(name = "Aulas", description = "Contém todas as operações relativas aos recursos de CRUD de aula.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/aulas")
public class AulaController {

    private final AulaService aulaService;

    @Operation(summary = "Criar uma nova aula", description = "Recurso para criar uma nova aula",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aula.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Aula já cadastrada no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Aula> createAula(@RequestBody AulaDTO aulaDTO) {
        try {
            Aula savedAula = aulaService.salvar(aulaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAula);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Recuperar uma aula pelo id", description = "Recuperar uma aula pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aula.class))),
                    @ApiResponse(responseCode = "404", description = "Aula não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Aula> getAulaById(@PathVariable Long id) {
        Aula aula = aulaService.buscarPorId(id);
        return ResponseEntity.ok(aula);
    }

    @Operation(summary = "Editar uma aula existente", description = "Editar uma aula existente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aula atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aula.class))),
                    @ApiResponse(responseCode = "404", description = "Aula não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Aula> updateAula(@PathVariable Long id, @RequestBody AulaDTO aulaDTO) {
        Aula updatedAula = aulaService.editar(id, aulaDTO);
        return ResponseEntity.ok(updatedAula);
    }

    @Operation(summary = "Excluir aula", description = "Recurso para excluir uma aula pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Aula excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Aula não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        aulaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recuperar todas as aulas", description = "Recuperar todas as aulas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recursos recuperados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AulaProjection.class)))
            })
    @GetMapping
    public ResponseEntity<List<AulaProjection>> getAllAulas() {
        List<AulaProjection> aulas = aulaService.buscarTodos();
        return ResponseEntity.ok(aulas);
    }
}
