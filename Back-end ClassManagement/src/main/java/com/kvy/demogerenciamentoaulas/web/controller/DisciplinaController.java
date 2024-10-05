package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.service.DisciplinaService;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.LoginResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.DisciplinaMapper;
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

@Tag(name = "Disciplinas", description = "Contém todas as operações relativas aos recursos de CRUD de disciplina.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @Operation(summary = "Criar uma nova disciplina", description = "Recurso para criar uma nova disciplina",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisciplinaResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Disciplina já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<DisciplinaResponseDto> createDisciplina(@Valid @RequestBody DisciplinaCreateDto disciplinaCreateDto) {
        Disciplina savedDisciplina = disciplinaService.salvar(DisciplinaMapper.toDisciplina(disciplinaCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(DisciplinaMapper.toDisciplinaDto(savedDisciplina));
    }

    @Operation(summary = "Recuperar uma disciplina pelo id", description = "Recuperar uma disciplina pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DisciplinaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDto> getDisciplinaById(@PathVariable Long id) {
        Disciplina disciplina = disciplinaService.buscarPorId(id);
        return ResponseEntity.ok(DisciplinaMapper.toDisciplinaDto(disciplina));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> updateDisciplina(@PathVariable Long id, @RequestBody Disciplina disciplina) {
        Disciplina updatedDisciplina = disciplinaService.editar(id, disciplina);
        return ResponseEntity.ok(updatedDisciplina);
    }

    @Operation(summary = "Excluir disciplina", description = "Recurso para excluir uma disciplina pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Disciplina excluída com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        disciplinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<DisciplinaResponseDto>> getDisciplinaAll(@PathVariable Long id) {
        List<Disciplina> disciplinas = disciplinaService.buscarTodos(id);
        return ResponseEntity.ok(DisciplinaMapper.toListDto(disciplinas));
    }
}
