package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.service.AulaService;
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
                    @ApiResponse(responseCode = "409", description = "aula já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Aula> createAula(@RequestBody Aula aula) {

        Aula savedAula = aulaService.salvar(aula);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAula);
    }

    @Operation(summary = "Recuperar uma aula pelo id", description = "Recuperar uma aula pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aula.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Aula> getAulaById(@PathVariable Long id) {
        Aula aula = aulaService.buscarPorId(id);
        return ResponseEntity.ok(aula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aula> updateAula(@PathVariable Long id, @RequestBody Aula aula) {
        Aula updatedAula = aulaService.editar(id, aula);
        return ResponseEntity.ok(updatedAula);
    }


    @Operation(summary = "Excluir aula", description = "Recurso para excluir uma aula pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Aula excluída com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        aulaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Aula>> getAulaAll(@PathVariable Long id) {
        List<Aula> aulas = aulaService.buscarTodos(id);
        return ResponseEntity.ok(aulas);
    }

}
