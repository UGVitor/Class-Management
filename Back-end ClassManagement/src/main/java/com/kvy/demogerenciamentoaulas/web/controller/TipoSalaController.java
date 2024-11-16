package com.kvy.demogerenciamentoaulas.web.controller;
import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.repository.Projection.CursoProjection;
import com.kvy.demogerenciamentoaulas.service.TipoSalaService;
import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;
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

@Tag(name = "TipoSalas", description = "Contem todas as operações relativas aos recursos de CRUD de Tipo de Sala")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/tiposalas")
public class TipoSalaController {

    private final TipoSalaService tipoSalaService;

    @Operation(summary = "Criar um novo curso", description = "Recurso para criar um novo curso",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Curso já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<TipoSalaDTO> createTipoSala(@Valid @RequestBody TipoSalaDTO tipoSalaDTO) {
        try {
            TipoSalaDTO savedTipoSala = tipoSalaService.salvar(tipoSalaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTipoSala);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Recuperar curso pelo ID", description = "Recurso para recuperar um curso específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
                    @ApiResponse(responseCode = "404", description = "Curso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<TipoSala> getById(@PathVariable Long id) {
        TipoSala tipoSala = tipoSalaService.buscarPorId(id);
        return ResponseEntity.ok(tipoSala);
    }

    @Operation(summary = "Excluir Tipo de Sala pelo ID", description = "Recurso para excluir um Tipo de Sala pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tipo de Sala excluído com sucesso",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Tipo de Sala não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoSala(@PathVariable Long id) {
        tipoSalaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Editar curso pelo ID", description = "Recurso para editar um curso específico pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
                    @ApiResponse(responseCode = "404", description = "Curso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<TipoSala> editarTipoSala(@PathVariable Long id, @RequestBody TipoSalaDTO tipoSalaDTO) {
        TipoSala tipoSalaAtualizado = tipoSalaService.editar(id, tipoSalaDTO);
        return ResponseEntity.ok(tipoSalaAtualizado);
    }

    @Operation(summary = "Listar todos os cursos", description = "Recurso para listar todos os cursos com o nome da modalidade",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de cursos recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CursoProjection.class)))
            })
    @GetMapping
    public ResponseEntity<List<TipoSala>> getAllTipoSalas() {
        List<TipoSala> tipoSalas = tipoSalaService.buscarTodos();
        return ResponseEntity.ok(tipoSalas);
    }
}
