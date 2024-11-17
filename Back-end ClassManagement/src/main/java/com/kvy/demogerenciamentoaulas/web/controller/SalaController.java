package com.kvy.demogerenciamentoaulas.web.controller;


import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.entity.Sala;
import com.kvy.demogerenciamentoaulas.repository.Projection.CursoProjection;
import com.kvy.demogerenciamentoaulas.repository.Projection.SalaProjection;
import com.kvy.demogerenciamentoaulas.repository.SalaRepository;
import com.kvy.demogerenciamentoaulas.service.SalaService;
import com.kvy.demogerenciamentoaulas.web.dto.CursoDTO;
import com.kvy.demogerenciamentoaulas.web.dto.SalaDTO;
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

@Tag(name = "Salas", description = "Contém todas as operações relativas aos recursos de CRUD de Sala.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/salas")
public class SalaController {


    private final SalaService salaService;

    @Operation(summary = "Criar uma nova sala", description = "Recurso para criar uma nova sala",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sala.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Sala já cadastrada no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Sala> save(@RequestBody SalaDTO salaDTO) {
        try{
            System.out.println("SalaDTO recebido : " + salaDTO);
            Sala savedSala = salaService.salvar(salaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSala);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Recuperar uma sala pelo ID", description = "Recurso para recuperar uma sala específica pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sala recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sala.class))),
                    @ApiResponse(responseCode = "404", description = "Sala não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Sala> getById(@PathVariable Long id) {
        Sala sala = salaService.buscarPorId(id);
        return ResponseEntity.ok(sala);
    }

    @Operation(summary = "Excluir sala pelo ID", description = "Recurso para excluir uma sala pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sala excluída com sucesso",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Sala não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
        salaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Editar sala pelo ID", description = "Recurso para editar uma sala específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sala atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sala.class))),
                    @ApiResponse(responseCode = "404", description = "Sala não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Sala> editarSala(@PathVariable Long id, @RequestBody SalaDTO salaDTO) {
        Sala salaAtualizada = salaService.editar(id, salaDTO);
        return ResponseEntity.ok(salaAtualizada);
    }

    @Operation(summary = "Listar todas as salas", description = "Recurso para listar todos as salas com o nome do Tipo de Sala",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de salas recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalaProjection.class)))
            })
    @GetMapping
    public ResponseEntity<List<SalaProjection>> getAllCursos() {
        List<SalaProjection> salas = salaService.buscarTodasSalas();
        return ResponseEntity.ok(salas);
    }
}
