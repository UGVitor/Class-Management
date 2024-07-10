package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.service.CursoService;
import com.kvy.demogerenciamentoaulas.web.dto.CursoResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.CursoCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.CursoResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.CursoMapper;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.DisciplinaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/curso")
public class CursoController {

    private final CursoService cursoService;
    @PostMapping
    public ResponseEntity<CursoResponseDto> createCurso(@Valid @RequestBody CursoCreateDto cursoCreateDto) {

        Curso savedCurso = cursoService.salvar(CursoMapper.toCurso(cursoCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CursoMapper.toCursoDto(savedCurso));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDto> getById(@PathVariable Long id) {
        Curso curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(CursoMapper.toCursoDto(curso));
    }
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
