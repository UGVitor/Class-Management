package com.kvy.demogerenciamentoaulas.service;


import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.exception.CursoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.TipoSalaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.Projection.CursoProjection;
import com.kvy.demogerenciamentoaulas.repository.TipoSalaRepository;
import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TipoSalaService {
    private final TipoSalaRepository tipoSalaRepository;

    @Transactional
    public TipoSalaDTO salvar(TipoSalaDTO tipoSalaDTO) {
        if (tipoSalaDTO.getTipoSala() == null || tipoSalaDTO.getTipoSala().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo tipoSala não pode ser nulo ou vazio");
        }

        TipoSala tipoSala = new TipoSala();
        tipoSala.setTipoSala(tipoSalaDTO.getTipoSala());

        TipoSala savedTipoSala = tipoSalaRepository.save(tipoSala);
        return toDTO(savedTipoSala);
    }

    @Transactional
    public TipoSala buscarPorId(Long id) {
        return tipoSalaRepository.findById(id).orElseThrow(() -> new TipoSalaEntityNotFoundException(String.format("Curso id=%s não encontrado", id)));

    }

    @Transactional
    public TipoSala editar(Long id, TipoSalaDTO tipoSalaDTO) {
        TipoSala existingTipoSala = buscarPorId(id);
        existingTipoSala.setTipoSala(tipoSalaDTO.getTipoSala());

        return tipoSalaRepository.save(existingTipoSala);
    }

    @Transactional
    public void excluir(Long id) {
        Optional<TipoSala> optionalTipoSala = tipoSalaRepository.findById(id);
        if (optionalTipoSala.isPresent()) {
            tipoSalaRepository.delete(optionalTipoSala.get());
            System.out.println("Tipo de sala excluida com sucesso");
        }else {
            throw new RuntimeException("Tipo de Sala não encontrado com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<TipoSala> buscarTodos() {
        return tipoSalaRepository.findAll();
    }

    private TipoSalaDTO toDTO(TipoSala tipoSala) {
        return new TipoSalaDTO(tipoSala.getId(), tipoSala.getTipoSala());
    }
}
