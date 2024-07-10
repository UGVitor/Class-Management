package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.service.DisciplinaService;
import com.kvy.demogerenciamentoaulas.web.dto.AulaResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.AulaMapper;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.DisciplinaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<DisciplinaResponseDto> createDisciplina(@Valid @RequestBody DisciplinaCreateDto disciplinaCreateDto) {

        Disciplina savedDisciplina = disciplinaService.salvar(DisciplinaMapper.toDisciplina(disciplinaCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(DisciplinaMapper.toDisciplinaDto(savedDisciplina));
    }

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
