package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<Disciplina> createDisciplina(@RequestBody Disciplina disciplina) {
        Disciplina savedDisciplina = disciplinaService.salvar(disciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDisciplina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> getDisciplinaById(@PathVariable Long id) {
        Disciplina disciplina = disciplinaService.buscarPorId(id);
        return ResponseEntity.ok(disciplina);
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
}
