package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.service.TurnoService;
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
@Tag(name = "Turno", description = "Contém todas as operações reletivas aos recursos de CRUD de turno.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/turno")

public class TurnoController {

    private final TurnoService turnoService;

    @Operation(summary = "Criar um novo turno", description = "Recurso para criar um novo turno",
            responses = {

                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turno.class))),
                    @ApiResponse(responseCode = "409", description = "aula já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PostMapping("/{id}")
    public ResponseEntity<Turno> createTurno(@RequestBody Turno turno) {

        Turno savedTurno = turnoService.salvar(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTurno);
    }

    @Operation(summary = "Recuperar um turno pelo id", description = "Recuperar um turno pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turno.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @GetMapping("/{id}")
    public ResponseEntity<Turno> getTurnoById(@PathVariable Long id) {
        Turno turno = turnoService.buscarPorId(id);
        return ResponseEntity.ok(turno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turno> updateTurno(@PathVariable Long id, @RequestBody Turno turno) {
        Turno updatedTurno = turnoService.editar(id, turno);
        return ResponseEntity.ok(updatedTurno);
    }

    @Operation(summary = "Excluir turno", description = "Recurso para excluir um turno pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Turno excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        turnoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Turno>> getTurnoAll(@PathVariable Long id) {
        List<Turno> turnos = turnoService.buscarTodos(id);
        return ResponseEntity.ok(turnos);
    }

}
