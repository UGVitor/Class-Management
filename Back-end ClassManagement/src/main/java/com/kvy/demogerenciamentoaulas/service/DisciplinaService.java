package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.exception.DisciplinaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.DisciplinaRepository;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.DisciplinaProjection;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DisciplinaService {

    private static final Logger logger = LoggerFactory.getLogger(DisciplinaService.class);

    private final DisciplinaRepository disciplinaRepository;
    private final LoginRepository loginRepository;

    @Transactional
    public Disciplina salvar(DisciplinaDTO disciplinaDTO) {
        Login login = loginRepository.findById(disciplinaDTO.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("Login não encontrado"));

        // Verifica se o perfil do login é do tipo "Professor"
        if (!"Professor".equalsIgnoreCase(login.getPerfil().getNome())) {
            throw new IllegalArgumentException("A disciplina só pode ser criada por um login com perfil de 'Professor'");
        }

        // Cria e preenche a entidade Disciplina
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(TratamentoDeString.capitalizeWords(disciplinaDTO.getNome()));
        disciplina.setLogin(login);

        // Salva e retorna a disciplina
        return disciplinaRepository.save(disciplina);
    }

    @Transactional(readOnly = true)
    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaEntityNotFoundException(
                        String.format("Disciplina com id=%s não encontrada", id)));
    }

    @Transactional
    public Disciplina editar(Long id, DisciplinaDTO disciplinaDTO) {
        Disciplina existingDisciplina = buscarPorId(id);
        existingDisciplina.setNome(TratamentoDeString.capitalizeWords(disciplinaDTO.getNome()));

        Login login = loginRepository.findById(disciplinaDTO.getLoginId()).orElseThrow(() -> new IllegalArgumentException("Login não encontrado"));
        if (!"Professor".equalsIgnoreCase(login.getPerfil().getNome())) {
            throw new IllegalArgumentException("A disciplina só pode ser associada a um login com perfil de 'Professor'");
        }

        existingDisciplina.setLogin(login);


        return disciplinaRepository.save(existingDisciplina);
    }



    @Transactional
    public void excluir(Long id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new DisciplinaEntityNotFoundException(
                    String.format("Disciplina com id=%s não encontrada", id));
        }
        disciplinaRepository.deleteById(id);
        DisciplinaService.logger.info("Disciplina com id={} excluída com sucesso", id);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaProjection> buscarTodos() {
        return disciplinaRepository.findAllDisciplinaWithLoginAndTurma();
    }
}
