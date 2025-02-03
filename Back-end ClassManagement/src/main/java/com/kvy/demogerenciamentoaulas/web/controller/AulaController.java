package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.repository.AulaRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.AulaProjection;
import com.kvy.demogerenciamentoaulas.service.AulaService;
import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Aulas", description = "Contém todas as operações relativas aos recursos de CRUD de aula.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/aulas")


public class AulaController {

    private final AulaService aulaService;

    @Operation(summary = "Criar uma nova aula", description = "Recurso para criar uma nova aula",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aula.class))),
                    @ApiResponse(responseCode = "409", description = "Aula já cadastrada.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Aula> createAula(@Valid @RequestBody AulaDTO aulaDTO) {
        Aula savedAula = aulaService.salvar(aulaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAula);

    }


    @Operation(summary = "Recuperar aula pelo ID", description = "Recurso para recuperar uma aula específica pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aula recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aula.class))),
                    @ApiResponse(responseCode = "404", description = "Aula não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Aula> getById(@PathVariable Long id) {
        Aula aula = aulaService.buscarPorId(id);
        return ResponseEntity.ok(aula);
    }

    @Operation(summary = "Excluir aula pelo ID", description = "Recurso para excluir uma aula pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Aula excluída com sucesso",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Aula não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        aulaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Editar aula pelo ID", description = "Recurso para editar uma aula específica pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aula atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aula.class))),
                    @ApiResponse(responseCode = "404", description = "Aula não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Aula> editarAula(@PathVariable Long id, @Valid @RequestBody AulaDTO aulaDTO) {
            return ResponseEntity.ok(aulaService.editar(id, aulaDTO));
    }


    @Operation(summary = "Listar todas as aulas", description = "Recurso para listar todas as aulas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de aulas recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AulaDTO.class)))
            })
    @GetMapping
    public ResponseEntity<List<AulaProjection>> getAllAulas() {
        List<AulaProjection> aulas = aulaService.buscarTodos();
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("/por-dia")
    public ResponseEntity<List<AulaProjection>> buscarAulasPorDia(@RequestParam String diaSemana) {
        List<AulaProjection> aulas = aulaService.buscarAulasPorDia(diaSemana);
        return ResponseEntity.ok(aulas);
    }


}
