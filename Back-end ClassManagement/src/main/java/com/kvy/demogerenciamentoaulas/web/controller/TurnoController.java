package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.service.TurnoService;
import com.kvy.demogerenciamentoaulas.web.dto.TurnoDTO;
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

@Tag(name = "Turnos", description = "Contém todas as operações relativas aos recursos de CRUD de turno.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    @Operation(
            summary = "Criar um novo turno",
            description = "Recurso para criar um novo turno",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turno.class))),
                    @ApiResponse(responseCode = "409", description = "Turno já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado devido a dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<Turno> createTurno(@Valid @RequestBody TurnoDTO turnoDTO) {
        Turno savedTurno = turnoService.salvar(turnoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTurno);
    }

    @Operation(
            summary = "Recuperar um turno pelo ID",
            description = "Recurso para recuperar um turno específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turno.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Turno> getTurnoById(@PathVariable Long id) {
        Turno turno = turnoService.buscarPorId(id);
        return ResponseEntity.ok(turno);
    }

    @Operation(
            summary = "Atualizar um turno",
            description = "Recurso para atualizar as informações de um turno pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turno.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado devido a dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Turno> updateTurno(@PathVariable Long id, @Valid @RequestBody TurnoDTO turnoDTO) {
        Turno updatedTurno = turnoService.editar(id, turnoDTO);
        return ResponseEntity.ok(updatedTurno);
    }

    @Operation(
            summary = "Excluir um turno",
            description = "Recurso para excluir um turno específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Turno excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        turnoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Recuperar todos os turnos",
            description = "Recurso para listar todos os turnos cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de turnos recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turno.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<Turno>> getTurnoAll() {
        List<Turno> turnos = turnoService.buscarTodos();
        return ResponseEntity.ok(turnos);
    }
}
