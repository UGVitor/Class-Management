package com.kvy.demogerenciamentoaulas.service;


import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.entity.Sala;
import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.exception.SalaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.Projection.SalaProjection;
import com.kvy.demogerenciamentoaulas.repository.SalaRepository;
import com.kvy.demogerenciamentoaulas.repository.TipoSalaRepository;
import com.kvy.demogerenciamentoaulas.web.dto.SalaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SalaService {

    private final SalaRepository salaRepository;

    private final TipoSalaRepository tipoSalaRepository;

    @Transactional
    public Sala salvar(SalaDTO salaDTO) {
        Sala sala = new Sala();
        sala.setSala(salaDTO.getSala());
        sala.setNumero(salaDTO.getNumero()); // Setar novo atributo
        sala.setCapacidade(salaDTO.getCapacidade()); // Setar novo atributo

        if (salaDTO.getTipoSala() != null) {
            TipoSala tiposala = tipoSalaRepository.findById(salaDTO.getTipoSala())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de Sala não encontrado"));
            sala.setTipoSala(tiposala);
        } else {
            throw new IllegalArgumentException("O ID do Tipo de Sala não pode ser nulo");
        }
        return salaRepository.save(sala);
    }


    @Transactional
    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new SalaEntityNotFoundException(String.format("Curso id=%s não encontrado", id)));
    }

    @Transactional
    public Sala editar(Long id, SalaDTO salaDTO) {
        Sala existingSala = buscarPorId(id);
        existingSala.setSala(salaDTO.getSala());
        existingSala.setNumero(salaDTO.getNumero()); // Atualizar novo atributo
        existingSala.setCapacidade(salaDTO.getCapacidade()); // Atualizar novo atributo

        if (salaDTO.getTipoSala() != null) {
            TipoSala tipoSala = tipoSalaRepository.findById(salaDTO.getTipoSala())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de Sala não encontrado com o ID: " + salaDTO.getTipoSala()));
            existingSala.setTipoSala(tipoSala);
        } else {
            throw new IllegalArgumentException("O ID do Tipo de Sala não pode ser nulo");
        }

        return salaRepository.save(existingSala);
    }


    @Transactional
    public void excluir(Long id) {
        Optional<Sala> optionalSala = salaRepository.findById(id);
        if (optionalSala.isPresent()) {
            salaRepository.delete(optionalSala.get());
            System.out.println("Sala excluida com sucesso");
        } else {
            throw new RuntimeException("Saça não encontrada com o ID: " + id);
        }
   }

   @Transactional
   public List<SalaProjection> buscarTodasSalas() {
        return salaRepository.findAllSalas();
   }



}
