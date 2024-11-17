package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Curso;

import com.kvy.demogerenciamentoaulas.repository.Projection.CursoProjection;
import com.kvy.demogerenciamentoaulas.service.CursoService;
import com.kvy.demogerenciamentoaulas.web.dto.CursoDTO;
import com.kvy.demogerenciamentoaulas.web.dto.ResponseDTO.CursoResponseDTO;
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
import java.util.stream.Collectors;

@Tag(name = "Cursos", description = "Contém todas as operações relativas aos recursos de CRUD de curso.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/curso")
public class CursoController {

    private final CursoService cursoService;

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
    public ResponseEntity<Curso> createCurso(@RequestBody CursoDTO cursoDTO) {
        try {
            // Logando o DTO recebido
            System.out.println("CursoDTO recebido: " + cursoDTO);
            Curso savedCurso = cursoService.salvar(cursoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCurso);
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
    public ResponseEntity<Curso> getById(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(curso);
    }

    @Operation(summary = "Excluir curso pelo ID", description = "Recurso para excluir um curso pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Curso excluído com sucesso",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Curso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.excluir(id);
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
    public ResponseEntity<Curso> editarCurso(@PathVariable Long id, @RequestBody CursoDTO cursoDTO) {
        Curso cursoAtualizado = cursoService.editar(id, cursoDTO);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @Operation(summary = "Listar todos os cursos", description = "Recurso para listar todos os cursos com o nome da modalidade",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de cursos recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CursoProjection.class)))
            })
    @GetMapping
    public ResponseEntity<List<CursoProjection>> getAllCursos() {
        List<CursoProjection> cursos = cursoService.buscarTodos();
        return ResponseEntity.ok(cursos);
    }

}
