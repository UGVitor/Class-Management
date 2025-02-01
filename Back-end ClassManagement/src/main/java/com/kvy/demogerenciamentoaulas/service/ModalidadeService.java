package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.exception.ModalidadeEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.ModalidadeUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.ModalidadeRepository;
import com.kvy.demogerenciamentoaulas.web.dto.ModalidadeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service

public class ModalidadeService {

    private final ModalidadeRepository modalidadeRepository;

    @Transactional
    public Modalidade salvar(ModalidadeDTO modalidadeDTO) {
        // Validação do DTO e campo obrigatório
        if (modalidadeDTO == null) {
            throw new IllegalArgumentException("ModalidadeDTO não pode ser nulo");
        }

        String nomeModalidade = modalidadeDTO.getNome();
        if (nomeModalidade == null || nomeModalidade.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da modalidade é obrigatório");
        }
        try {
            Modalidade modalidade = new Modalidade();
            modalidade.setNome(TratamentoDeString.capitalizeWords(modalidadeDTO.getNome()));
            return modalidadeRepository.save(modalidade);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new ModalidadeUniqueViolationException(String.format("Modalidade '%s' já cadastrado", modalidadeDTO.getNome()));
        }
    }

    @Transactional(readOnly = true)
    public Modalidade buscarPorId(Long id){
        return modalidadeRepository.findById(id).orElseThrow(
                () -> new ModalidadeEntityNotFoundException(String.format("Modalidade id=%s não encontrada", id))
        );
    }

    @Transactional
    public Modalidade editar(Long id, ModalidadeDTO modalidadeDTO) {
        // Validação do DTO e campo obrigatório
        if (modalidadeDTO == null) {
            throw new IllegalArgumentException("ModalidadeDTO não pode ser nulo");
        }

        String nomeModalidade = modalidadeDTO.getNome();
        if (nomeModalidade == null || nomeModalidade.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da modalidade é obrigatório");
        }
        Modalidade existingModalidade = buscarPorId(id);
        existingModalidade.setNome(TratamentoDeString.capitalizeWords(modalidadeDTO.getNome()));
        return modalidadeRepository.save(existingModalidade);
    }

    @Transactional
    public void excluir(Long id) {
        Modalidade optionalModalidade = buscarPorId(id);
        modalidadeRepository.delete(optionalModalidade);
        System.out.println("Deletado com Sucesso!");
    }

    @Transactional(readOnly = true)
    public List<Modalidade> buscarTodos() {
        return modalidadeRepository.findAll();
    }
}


