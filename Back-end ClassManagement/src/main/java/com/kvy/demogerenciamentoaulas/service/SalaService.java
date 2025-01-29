package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Sala;
import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.exception.SalaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.SalaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.exception.TipoSalaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.Projection.SalaProjection;
import com.kvy.demogerenciamentoaulas.repository.SalaRepository;
import com.kvy.demogerenciamentoaulas.repository.TipoSalaRepository;
import com.kvy.demogerenciamentoaulas.web.dto.SalaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final TipoSalaService tipoSalaService;

    @Transactional
    public Sala salvar(SalaDTO salaDTO) {
        try {
            Sala sala = new Sala();
            sala.setNumero(salaDTO.getNumero());
            sala.setCapacidade(salaDTO.getCapacidade());


            TipoSala tiposala = tipoSalaService.buscarPorId(salaDTO.getTipoSala());
            sala.setTipoSala(tiposala);

            return salaRepository.save(sala);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new SalaUniqueViolationException("Tipo sala '%s' já cadastrado");
        }
    }


    @Transactional
    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new SalaEntityNotFoundException(String.format("Curso id=%s não encontrado", id)));
    }

    @Transactional
    public Sala editar(Long id, SalaDTO salaDTO) {
        Sala existingSala = buscarPorId(id);
        existingSala.setNumero(salaDTO.getNumero());
        existingSala.setCapacidade(salaDTO.getCapacidade());

        TipoSala tipoSala = tipoSalaService.buscarPorId(salaDTO.getTipoSala());
        existingSala.setTipoSala(tipoSala);

        return salaRepository.save(existingSala);
    }


    @Transactional
    public void excluir(Long id) {
        Sala optionalSala = buscarPorId(id);
        salaRepository.delete(optionalSala);
        System.out.println("Sala excluida com sucesso");
   }

   @Transactional
   public List<SalaProjection> buscarTodasSalas() {
        return salaRepository.findAllSalas();
   }



}
