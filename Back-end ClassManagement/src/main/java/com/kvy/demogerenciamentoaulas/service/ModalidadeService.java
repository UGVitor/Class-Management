package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.exception.ModalidadeEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.ModalidadeRepository;
import com.kvy.demogerenciamentoaulas.web.dto.ModalidadeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class ModalidadeService {

    private final ModalidadeRepository modalidadeRepository;

    @Transactional
    public Modalidade salvar(ModalidadeDTO modalidadeDTO) {
        if (modalidadeDTO.getNome() == null || modalidadeDTO.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da Modalidade não pode ser nulo ou vazio");
        }
        Modalidade modalidade = new Modalidade();
        modalidade.setNome(modalidadeDTO.getNome());
        return modalidadeRepository.save(modalidade);
    }

    @Transactional(readOnly = true)
    public Modalidade buscarPorId(Long id){
        return modalidadeRepository.findById(id).orElseThrow(
                () -> new ModalidadeEntityNotFoundException(String.format("Modalidade id=%s não encontrada", id))
        );
    }

    @Transactional
    public Modalidade editar(Long id, ModalidadeDTO modalidadeDTO) {
        Modalidade existingModalidade = modalidadeRepository.findById(id).orElseThrow(() -> new ModalidadeEntityNotFoundException(String.format("Modalidade id=%s não encontrado", id)));

        if (modalidadeDTO.getNome() == null || modalidadeDTO.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da Modalidade não pode ser nulo ou vazio");
        }
        existingModalidade.setNome(modalidadeDTO.getNome());
        return modalidadeRepository.save(existingModalidade);

    }

    @Transactional
    public void excluir(Long id) {
        Optional<Modalidade> optionalModalidade = modalidadeRepository.findById(id);
        if (optionalModalidade.isPresent()) {
            modalidadeRepository.delete(optionalModalidade.get());
            System.out.println("Deletado com Sucesso!");
        } else {
            throw new RuntimeException("Modalidade não encontrado com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Modalidade> buscarTodos() {
        return modalidadeRepository.findAll();
    }
}


