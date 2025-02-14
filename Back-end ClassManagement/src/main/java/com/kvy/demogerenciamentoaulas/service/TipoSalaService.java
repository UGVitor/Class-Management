package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.exception.LoginUniqueViolationException;
import com.kvy.demogerenciamentoaulas.exception.TipoSalaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.TipoSalaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.TipoSalaRepository;
import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;
import jakarta.annotation.PostConstruct;
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
        if (tipoSalaDTO == null) {
            throw new IllegalArgumentException("O tipo de sala não pode ser nulo");
        }

        if (tipoSalaDTO.getTipoSala() == null) {
            throw new IllegalArgumentException("O nome do Tipo de Sala é obrigatório");
        }
        try {
            TipoSala tipoSala = new TipoSala();
            tipoSala.setTipoSala(TratamentoDeString.capitalizeWords(tipoSalaDTO.getTipoSala()));
            return toDTO(tipoSalaRepository.save(tipoSala));
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new TipoSalaUniqueViolationException(String.format("Tipo sala '%s' já cadastrado", tipoSalaDTO.getTipoSala()));
        }

    }

    @Transactional
    public TipoSala buscarPorId(Long id) {
        return tipoSalaRepository.findById(id).orElseThrow(() -> new TipoSalaEntityNotFoundException(String.format("Curso id=%s não encontrado", id)));

    }

    @Transactional
    public TipoSala editar(Long id, TipoSalaDTO tipoSalaDTO) {
        if (tipoSalaDTO == null) {
            throw new IllegalArgumentException("O tipo de sala não pode ser nulo");
        }

        if (tipoSalaDTO.getTipoSala() == null) {
            throw new IllegalArgumentException("O nome do Tipo de Sala é obrigatório");
        }
        TipoSala existingTipoSala = buscarPorId(id);
        existingTipoSala.setTipoSala(TratamentoDeString.capitalizeWords(tipoSalaDTO.getTipoSala()));
        return tipoSalaRepository.save(existingTipoSala);
    }

    @Transactional
    public void excluir(Long id) {
        TipoSala optionalTipoSala = buscarPorId(id);
        tipoSalaRepository.delete(optionalTipoSala);
        System.out.println("Tipo de sala excluida com sucesso");
    }

    @Transactional(readOnly = true)
    public List<TipoSala> buscarTodos() {
        return tipoSalaRepository.findAll();
    }

    public TipoSalaDTO toDTO(TipoSala tipoSala) {
        return new TipoSalaDTO(tipoSala.getId(), tipoSala.getTipoSala());
    }

    @PostConstruct
    @Transactional
    public void adicionarTipoSalaPadrao() {
        adicionarTipoSalaSeNaoExistir("Laboratório");
        adicionarTipoSalaSeNaoExistir("Sala de Aula");
    }

    private void adicionarTipoSalaSeNaoExistir(String nomeTipoSala) {
        if (!tipoSalaRepository.existsByTipoSala(nomeTipoSala)) {
            TipoSala tipoSala = new TipoSala();
            tipoSala.setTipoSala(nomeTipoSala);
            tipoSalaRepository.save(tipoSala);
        }
    }
    public Long getTipoSalaId() {
        return 0L;
    }
}
