package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.service.CursoService;
import com.kvy.demogerenciamentoaulas.web.dto.CursoResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.CursoCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.CursoMapper;
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

@Tag(name = "Cursos", description = "Contém todas as operações relativas aos recursos de CRUD de curso.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/curso")
public class CursoController {

    private final CursoService cursoService;

    @Operation(summary = "Criar um novo curso", description = "Recurso para criar um novo curso",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CursoResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "curso já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<CursoResponseDto> createCurso(@Valid @RequestBody CursoCreateDto cursoCreateDto) {

        Curso savedCurso = cursoService.salvar(CursoMapper.toCurso(cursoCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CursoMapper.toCursoDto(savedCurso));
    }

    @Operation(summary = "Recuperar um curso pelo id", description = "Recuperar um curso pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CursoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recursos não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDto> getById(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(CursoMapper.toCursoDto(curso));
    }

    @Operation(summary = "Excluir curso", description = "Recurso para excluir um curso pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Curso excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<CursoResponseDto>> getCursoAll(@PathVariable Long id) {
        List<Curso> cursos = cursoService.buscarTodos(id);
        return ResponseEntity.ok(CursoMapper.toListDto(cursos));
    }
}
