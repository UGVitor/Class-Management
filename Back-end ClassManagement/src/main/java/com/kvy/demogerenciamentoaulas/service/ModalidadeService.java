package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.exception.ModalidadeEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.ModalidadeRepository;
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
    public Modalidade salvar(Modalidade modalidade) {
        return modalidadeRepository.save(modalidade);
    }

    @Transactional(readOnly = true)
    public Modalidade buscarPorId(Long id){
        return modalidadeRepository.findById(id).orElseThrow(
                () -> new ModalidadeEntityNotFoundException(String.format("Modalidade id=%s não encontrada", id))
        );
    }

    @Transactional
    public Modalidade editar(Long id, Modalidade modalidade) {
        Modalidade existingModalidade = modalidadeRepository.findById(id).orElseThrow(() -> new ModalidadeEntityNotFoundException(String.format("Modalidade id=%s não encontrado", id)));

        existingModalidade.setNome(modalidade.getNome());
        return modalidadeRepository.save(existingModalidade);

    }

    @Transactional
    public void excluir(Long id) {
        Optional<Modalidade> optionalUser = modalidadeRepository.findById(id);
        if (optionalUser.isPresent()) {
            modalidadeRepository.delete(optionalUser.get());
            System.out.println("Deletado com Sucesso!");
        } else {
            throw new RuntimeException("Modalidade não encontrada com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Modalidade> buscarTodos() {
        return modalidadeRepository.findAll();
    }
}


