package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Horario;
import com.kvy.demogerenciamentoaulas.service.HorarioService;
import com.kvy.demogerenciamentoaulas.web.dto.HorarioDTO;
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

@Tag(name = "Horario", description = "Contém todas as operações relativas aos recursos de CRUD de horario.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    @Operation(summary = "Criar um novo horario", description = "Recurso para criar um novo horario",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class))),
                    @ApiResponse(responseCode = "409", description = "Horario já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<HorarioDTO> createHorario(@Valid @RequestBody HorarioDTO horarioDTO) {
        Horario savedHorario = horarioService.salvar(horarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.convertToDTO(savedHorario));
    }

    @Operation(summary = "Recuperar um horario pelo id", description = "Recuperar um horario pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<HorarioDTO> getHorarioById(@PathVariable Long id) {
        Horario horario = horarioService.buscarPorId(id);
        return ResponseEntity.ok(horarioService.convertToDTO(horario));
    }

    @Operation(summary = "Atualizar horario", description = "Recurso para atualizar um horario pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Horario atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<HorarioDTO> updateHorario(@PathVariable Long id,@Valid @RequestBody HorarioDTO horarioDTO) {
        if (!horarioDTO.isHorarioValido()) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de término");
        }
        Horario horario = horarioService.convertToEntity(horarioDTO);
        Horario updatedHorario = horarioService.editar(id, horario);
        return ResponseEntity.ok(horarioService.convertToDTO(updatedHorario));
    }

    @Operation(summary = "Excluir Horario", description = "Recurso para excluir um Horario pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Horario excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        horarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os Horarios", description = "Recuperar todos os Horarios",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recursos recuperados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class)))
            })
    @GetMapping
    public ResponseEntity<List<Horario>> getHorarioAll() {
        List<Horario> horarios = horarioService.buscarTodos();
        return ResponseEntity.ok(horarios);
    }
}
