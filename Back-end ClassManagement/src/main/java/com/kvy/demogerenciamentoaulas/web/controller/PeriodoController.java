package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.service.PeriodoService;
import com.kvy.demogerenciamentoaulas.web.dto.PeriodoDTO;
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

@Tag(name = "Periodos", description = "Contém todas as operações relativas aos recursos de CRUD de periodo.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/periodos")

public class PeriodoController {

    private final PeriodoService periodoService;

    @Operation(summary = "Criar um novo periodo", description = "Recurso para criar um novo periodo",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Periodo.class))),
                    @ApiResponse(responseCode = "409", description = "periodo já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<PeriodoDTO> createPeriodo(@Valid @RequestBody PeriodoDTO periodoDTO) {
        Periodo savedPeriodo = periodoService.salvar(periodoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(periodoService.toDTO(savedPeriodo));
    }

    @Operation(summary = "Recuperar um periodo pelo id", description = "Recuperar um periodo pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Periodo.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<PeriodoDTO> getPeriodoById(@PathVariable Long id) {
        Periodo periodo = periodoService.buscarPorId(id);
        return ResponseEntity.ok(periodoService.toDTO(periodo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodoDTO> updatePeriodo(@PathVariable Long id, @Valid @RequestBody PeriodoDTO periodoDTO) {
        Periodo updatedPeriodo = periodoService.editar(id, periodoDTO);
        return ResponseEntity.ok(periodoService.toDTO(updatedPeriodo));
    }

    @Operation(summary = "Excluir periodo", description = "Recurso para excluir um periodo pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Periodo excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriodo(@PathVariable Long id) {
        periodoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Periodo>> getPeriodoAll() {
        List<Periodo> periodos = periodoService.buscarTodos();
        return ResponseEntity.ok(periodos);
    }
}
