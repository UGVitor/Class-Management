package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.service.SemestreService;
import com.kvy.demogerenciamentoaulas.web.dto.SemestreDTO;
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

@Tag(name = "Semestres", description = "Contém todas as operações relativas aos recursos de CRUD de semestre.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/semestres")

public class SemestreController {

    private final SemestreService semestreService;

    @Operation(summary = "Criar um novo semestre", description = "Recurso para criar um novo semestre",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Semestre.class))),
                    @ApiResponse(responseCode = "409", description = "Semestre já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Semestre> createSemestre(@Valid @RequestBody SemestreDTO semestreDTO) {
        Semestre savedSemestre = semestreService.salvar(semestreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSemestre);
    }

    @Operation(summary = "Recuperar um semestre pelo id", description = "Recuperar um semestre pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Semestre.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @GetMapping("/{id}")
    public ResponseEntity<Semestre> getSemestreById(@PathVariable Long id) {
        Semestre semestre = semestreService.buscarPorId(id);
        return ResponseEntity.ok(semestre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semestre> updateSemestre(@PathVariable Long id,@Valid @RequestBody SemestreDTO semestreDTO) {
        Semestre updatedSemestre = semestreService.editar(id, semestreDTO);
        return ResponseEntity.ok(updatedSemestre);
    }

    @Operation(summary = "Excluir semestre", description = "Recurso para excluir um semestre pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Semestre excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemestre(@PathVariable Long id) {
        semestreService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Semestre>> getSemestreAll() {
        List<Semestre> semestres = semestreService.buscarTodos();
        return ResponseEntity.ok(semestres);
    }
}
