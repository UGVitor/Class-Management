package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.service.AulaService;
import com.kvy.demogerenciamentoaulas.web.dto.AulaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.AulaResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.mapper.AulaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/aulas")
public class AulaController {

    private final AulaService aulaService;

    @PostMapping
    public ResponseEntity<AulaResponseDto> createAula(@Valid @RequestBody AulaCreateDto aulaCreateDto) {

        Aula savedAula = aulaService.salvar(AulaMapper.toAula(aulaCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(AulaMapper.toAulaDto(savedAula));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDto> getAulaById(@PathVariable Long id) {
        Aula aula = aulaService.buscarPorId(id);
        return ResponseEntity.ok(AulaMapper.toAulaDto(aula));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aula> updateAula(@PathVariable Long id, @RequestBody Aula aula) {
        Aula updatedAula = aulaService.editar(id, aula);
        return ResponseEntity.ok(updatedAula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aula> updateStatus(@PathVariable Long id) {
        Aula updatedStatus = aulaService.editarStatus(id);
        return ResponseEntity.ok(updatedStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        aulaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AulaResponseDto>> getAulaAll(@PathVariable Long id) {
        List<Aula> aulas = aulaService.buscarTodos(id);
        return ResponseEntity.ok(AulaMapper.toListDto(aulas));
    }

}
