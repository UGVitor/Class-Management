package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.service.AulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/aulas")
public class AulaController {

    private final AulaService aulaService;

    @PostMapping
    public ResponseEntity<Aula> createAula(@RequestBody Aula aula) {
        Aula savedAula = aulaService.salvar(aula);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAula);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aula> getAulaById(@PathVariable Long id) {
        Aula aula = aulaService.buscarPorId(id);
        return ResponseEntity.ok(aula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aula> updateAula(@PathVariable Long id, @RequestBody Aula aula) {
        Aula updatedAula = aulaService.editar(id, aula);
        return ResponseEntity.ok(updatedAula);
    }

<<<<<<< HEAD
    @PutMapping("/{id}")
    public ResponseEntity<Aula> updateStatus(@PathVariable Long id) {
        Aula updatedStatus = aulaService.editarStatus(id);
        return ResponseEntity.ok(updatedStatus);
    }

=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        aulaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
<<<<<<< HEAD

    @GetMapping
    public ResponseEntity<List<Aula>> getAulaAll(@PathVariable Long id) {
        List<Aula> aulas = aulaService.buscarTodos(id);
        return ResponseEntity.ok(aulas);
    }

=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
}
