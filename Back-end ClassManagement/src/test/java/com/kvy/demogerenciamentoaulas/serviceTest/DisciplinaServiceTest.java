package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.Adapter.DisciplinaAdapter;
import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.DisciplinaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.ProfInvalidException;
import com.kvy.demogerenciamentoaulas.fixtures.DisciplinaDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.DisciplinaRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.DisciplinaProjection;
import com.kvy.demogerenciamentoaulas.service.DisciplinaService;
import com.kvy.demogerenciamentoaulas.service.LoginService;
import com.kvy.demogerenciamentoaulas.service.PerfilService;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @Mock
    private LoginService loginService;

    @Mock
    private PerfilService perfilService;

    @InjectMocks
    private DisciplinaService disciplinaService;

    @Test
    void deveSalvarUmaDisciplinaValida() {
        DisciplinaDTO disciplinaDTO = DisciplinaDTOFixture.fixtureDisciplinaDTO1();
        Login login = new Login();
        login.setId(1L);
        Perfil perfil = new Perfil("Professor");
        login.setPerfil(perfil);

        when(loginService.buscarPorId(disciplinaDTO.getLoginId())).thenReturn(login);
        when(disciplinaRepository.save(any(Disciplina.class))).thenAnswer(invocation -> {
            Disciplina disciplina = invocation.getArgument(0);
            disciplina.setId(1L);
            return disciplina;
        });

        Disciplina disciplinaSalva = disciplinaService.salvar(disciplinaDTO);

        assertNotNull(disciplinaSalva);
        assertEquals(disciplinaDTO.getNome(), disciplinaSalva.getNome());
        assertEquals(disciplinaDTO.getLoginId(), disciplinaSalva.getLogin().getId());

        verify(loginService, times(1)).buscarPorId(disciplinaDTO.getLoginId());
        verify(disciplinaRepository, times(1)).save(any(Disciplina.class));
    }


    @Test
    void deveLancarExcecaoAoTentarSalvarDisciplinaComLoginNaoProfessor() {
        DisciplinaDTO disciplinaDTO = DisciplinaDTOFixture.fixtureDisciplinaDTO1();
        Login login = new Login();
        login.setId(1L);
        Perfil perfil = new Perfil(2L, "ALUNO");
        login.setPerfil(perfil);

        when(loginService.buscarPorId(1L)).thenReturn(login);

        assertThrows(ProfInvalidException.class, () -> {
            disciplinaService.salvar(disciplinaDTO);
        });
    }

    @Test
    void deveBuscarDisciplinaPorId() {
        DisciplinaDTO disciplinaDTO = DisciplinaDTOFixture.fixtureDisciplinaDTO1();
        Login login = new Login();
        login.setId(1L);
        Disciplina disciplina = DisciplinaAdapter.toEntity(disciplinaDTO, login);

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        Disciplina disciplinaEncontrada = disciplinaService.buscarPorId(1L);

        assertNotNull(disciplinaEncontrada);
        assertEquals(disciplinaDTO.getNome(), disciplinaEncontrada.getNome());
        assertEquals(disciplinaDTO.getLoginId(), disciplinaEncontrada.getLogin().getId());
    }

    @Test
    void deveLancarExcecaoAoTentarBuscarDisciplinaPorIdInexistente() {
        when(disciplinaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DisciplinaEntityNotFoundException.class, () -> {
            disciplinaService.buscarPorId(99L);
        });
    }

    @Test
    void deveEditarDisciplinaComIdValido() {
        DisciplinaDTO disciplinaDTO = DisciplinaDTOFixture.fixtureDisciplinaDTO1();
        disciplinaDTO.setLoginId(1L);

        Login login = new Login();
        login.setId(1L);
        Perfil perfil = new Perfil("Professor");
        login.setPerfil(perfil);

        Disciplina disciplinaExistente = new Disciplina();
        disciplinaExistente.setId(1L);
        disciplinaExistente.setNome("Matematica");
        disciplinaExistente.setLogin(login);

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplinaExistente));
        when(loginService.buscarPorId(disciplinaDTO.getLoginId())).thenReturn(login);
        when(disciplinaRepository.save(any(Disciplina.class))).thenAnswer(invocation -> {
            Disciplina disciplina = invocation.getArgument(0);
            disciplina.setId(1L);
            return disciplina;
        });

        Disciplina disciplinaEditada = disciplinaService.editar(1L, disciplinaDTO);

        assertNotNull(disciplinaEditada);
        assertEquals(disciplinaDTO.getNome(), disciplinaEditada.getNome());
        assertEquals(disciplinaDTO.getLoginId(), disciplinaEditada.getLogin().getId());

        verify(disciplinaRepository, times(1)).findById(1L);
        verify(loginService, times(1)).buscarPorId(disciplinaDTO.getLoginId());
        verify(disciplinaRepository, times(1)).save(any(Disciplina.class));
    }
    

    @Test
    void deveLancarExcecaoAoTentarEditarDisciplinaComLoginNaoProfessor() {
        DisciplinaDTO disciplinaDTO = DisciplinaDTOFixture.fixtureDisciplinaDTO1();
        Login login = new Login();
        login.setId(1L);
        Perfil perfil = new Perfil(2L, "ALUNO");
        login.setPerfil(perfil);

        Disciplina disciplinaExistente = new Disciplina();
        disciplinaExistente.setId(1L);
        disciplinaExistente.setNome("Matematica");
        disciplinaExistente.setLogin(login);

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplinaExistente));
        when(loginService.buscarPorId(1L)).thenReturn(login);

        assertThrows(ProfInvalidException.class, () -> {
            disciplinaService.editar(1L, disciplinaDTO);
        });
    }

    @Test
    void deveExcluirDisciplinaPorId() {
        DisciplinaDTO disciplinaDTO = DisciplinaDTOFixture.fixtureDisciplinaDTO1();
        Login login = new Login();
        login.setId(1L);
        Disciplina disciplina = DisciplinaAdapter.toEntity(disciplinaDTO, login);

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        disciplinaService.excluir(1L);

        verify(disciplinaRepository, times(1)).delete(disciplina);
    }

    @Test
    void deveLancarExcecaoAoTentarExcluirDisciplinaComIdInexistente() {
        when(disciplinaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DisciplinaEntityNotFoundException.class, () -> {
            disciplinaService.excluir(99L);
        });
    }

    @Test
    void deveBuscarTodasAsDisciplinasExistentes() {
        DisciplinaProjection disciplina1 = mock(DisciplinaProjection.class);
        DisciplinaProjection disciplina2 = mock(DisciplinaProjection.class);

        when(disciplina1.getId()).thenReturn(1L);
        when(disciplina1.getNome()).thenReturn("Matemática");
        when(disciplina2.getId()).thenReturn(2L);
        when(disciplina2.getNome()).thenReturn("Física");

        List<DisciplinaProjection> listaDisciplinas = List.of(disciplina1, disciplina2);
        when(disciplinaRepository.findAllDisciplinaWithLoginAndTurma()).thenReturn(listaDisciplinas);

        List<DisciplinaProjection> disciplinasEncontradas = disciplinaService.buscarTodos();

        assertNotNull(disciplinasEncontradas);
        assertEquals(2, disciplinasEncontradas.size());
        assertEquals(1L, disciplinasEncontradas.get(0).getId());
        assertEquals("Matemática", disciplinasEncontradas.get(0).getNome());
        assertEquals(2L, disciplinasEncontradas.get(1).getId());
        assertEquals("Física", disciplinasEncontradas.get(1).getNome());

        verify(disciplinaRepository, times(1)).findAllDisciplinaWithLoginAndTurma();
    }



    @Test
    void deveBuscarTodosAsDisciplinasEmUmaTabelaVazia() {
        when(disciplinaRepository.findAllDisciplinaWithLoginAndTurma()).thenReturn(Collections.emptyList());

        List<DisciplinaProjection> disciplinasEncontradas = disciplinaService.buscarTodos();

        assertNotNull(disciplinasEncontradas);
        assertTrue(disciplinasEncontradas.isEmpty());

        verify(disciplinaRepository, times(1)).findAllDisciplinaWithLoginAndTurma();
    }


}