package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.exception.CursoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.CursoUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.CursoRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.CursoProjection;
import com.kvy.demogerenciamentoaulas.web.dto.CursoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final ModalidadeService modalidadeService;

    @Transactional
    public Curso salvar(CursoDTO cursoDTO) {
        try {
            Curso curso = new Curso();
            curso.setCurso(TratamentoDeString.capitalizeWords(cursoDTO.getCurso()));
            Modalidade modalidade = modalidadeService.buscarPorId(cursoDTO.getModalidade());
            curso.setModalidade(modalidade);

            return cursoRepository.save(curso);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new CursoUniqueViolationException(String.format("Curso '%s' já cadastrado", cursoDTO.getCurso()));
        }
    }

    @Transactional
    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoEntityNotFoundException(String.format("Curso id=%s não encontrado", id)));
    }

    @Transactional
    public Curso editar(Long id, CursoDTO cursoDTO) {
        Curso existingCurso = buscarPorId(id);
        existingCurso.setCurso(cursoDTO.getCurso());
        Modalidade modalidade = modalidadeService.buscarPorId(cursoDTO.getModalidade());
        existingCurso.setModalidade(modalidade);
        return cursoRepository.save(existingCurso);
    }

    @Transactional
    public void excluir(Long id) {
        Curso optionalCurso = buscarPorId(id);
        cursoRepository.delete(optionalCurso);
        System.out.println("Deletado com Sucesso!");
    }

    @Transactional(readOnly = true)
    public List<CursoProjection> buscarTodos() {
        return cursoRepository.findAllCursosWithModalidadeNome();
    }
}
