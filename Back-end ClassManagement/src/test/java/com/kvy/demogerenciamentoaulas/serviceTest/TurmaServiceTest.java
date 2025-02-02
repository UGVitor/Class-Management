package com.kvy.demogerenciamentoaulas.serviceTest;
import com.kvy.demogerenciamentoaulas.Adapter.TurmaAdapter;
import com.kvy.demogerenciamentoaulas.Adapter.TurnoAdapter;
import com.kvy.demogerenciamentoaulas.entity.*;
import com.kvy.demogerenciamentoaulas.exception.TurmaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.TurnoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.TurmaDTOFixture;
import com.kvy.demogerenciamentoaulas.fixtures.TurnoDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.Projection.TurmaProjection;
import com.kvy.demogerenciamentoaulas.repository.TurmaRepository;
import com.kvy.demogerenciamentoaulas.repository.TurnoRepository;
import com.kvy.demogerenciamentoaulas.service.*;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;
import com.kvy.demogerenciamentoaulas.web.dto.TurnoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {


    @Mock
    private TurmaRepository turmaRepository;
    @Mock
    private CursoService cursoService;
    @Mock
    private TurnoService turnoService;
    @Mock
    private SemestreService semestreService;
    @Mock
    private PeriodoService periodoService;

    @InjectMocks
    private TurmaService turmaService;


    @Test
    void deveSalvarUmTurmaValido() {
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTO1();
        Turma turmaEsperado = TurmaAdapter.toEntity(turmaDTO);

        when(turmaRepository.save(Mockito.any(Turma.class))).thenReturn(turmaEsperado);

        Turma turmaSalvo = turmaService.salvar(turmaDTO);

        assertEquals(turmaEsperado.getNome(), turmaSalvo.getNome());
    }


    @Test
    void deveTentarSalvarUmaTurmaComNomeNulleRetornarIllegalArgumentException() {
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTONullName();

        assertThrows(IllegalArgumentException.class, () -> {
            turmaService.salvar(turmaDTO);
        });
    }

    @Test
    void deveTentarSalvarUmTurmaComNomeVazioeRetornarIllegalArgumentException() {
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () -> {
            turmaService.salvar(turmaDTO);
        });
    }


    @Test
    void deveBuscaroTurmaPorId() {
        Long turmaId = 1L;
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTO1();
        Turma turma = TurmaAdapter.toEntity(turmaDTO);

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turma));

        Turma turmaEncontrado = turmaService.buscarPorId(turmaId);

        assertNotNull(turmaEncontrado);
        assertEquals(turmaId, turmaEncontrado.getId());
    }

    @Test
    void deveTentarBuscaroTurmaPorIdInexistenteeRetornarTurnoEntityNotFoundException() {
        Long turmaId = 1L;

        assertThrows(TurmaEntityNotFoundException.class, () ->
        {turmaService.buscarPorId(turmaId);});
    }


    @Test
    void deveEditarUmTurmaComIdValido() {
        Long turmaId = 1L;
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTO1();
        turmaDTO.setNome("Turma Atualizado");
        turmaDTO.setPeriodo(1L);
        turmaDTO.setSemestre(1L);
        turmaDTO.setCurso(1L);
        turmaDTO.setTurno(1L);

        Periodo periodo = new Periodo();
        periodo.setId(1L);

        Semestre semestre = new Semestre();
        semestre.setId(1L);

        Curso curso = new Curso();
        curso.setId(1L);

        Turno turno = new Turno();
        turno.setId(1L);

        Turma turmaExistente = new Turma();
        turmaExistente.setId(turmaId);
        turmaExistente.setNome("Turma Antiga");
        turmaExistente.setPeriodo(periodo);
        turmaExistente.setSemestre(semestre);
        turmaExistente.setCurso(curso);
        turmaExistente.setTurno(turno);

        Turma turmaAtualizado = new Turma();
        turmaAtualizado.setId(turmaId);
        turmaAtualizado.setNome("Turma Atualizado");
        turmaAtualizado.setPeriodo(periodo);
        turmaAtualizado.setSemestre(semestre);
        turmaAtualizado.setCurso(curso);
        turmaAtualizado.setTurno(turno);

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turmaExistente));
        when(periodoService.buscarPorId(turmaDTO.getPeriodo())).thenReturn(periodo);
        when(semestreService.buscarPorId(turmaDTO.getSemestre())).thenReturn(semestre);
        when(cursoService.buscarPorId(turmaDTO.getCurso())).thenReturn(curso);
        when(turnoService.buscarPorId(turmaDTO.getTurno())).thenReturn(turno);
        when(turmaRepository.save(any(Turma.class))).thenReturn(turmaAtualizado);

        Turma turmaEditado = turmaService.editar(turmaId, turmaDTO);

        assertNotNull(turmaEditado, "A turma editada não deveria ser nula.");
        assertEquals("Turma Atualizado", turmaEditado.getNome(), "O nome da turma não foi atualizado corretamente.");
        assertEquals(1L, turmaEditado.getPeriodo().getId(), "O período da turma não foi atualizado corretamente.");
        assertEquals(1L, turmaEditado.getSemestre().getId(), "O semestre da turma não foi atualizado corretamente.");
        assertEquals(1L, turmaEditado.getCurso().getId(), "O curso da turma não foi atualizado corretamente.");
        assertEquals(1L, turmaEditado.getTurno().getId(), "O turno da turma não foi atualizado corretamente.");

        verify(turmaRepository, times(1)).findById(turmaId);
        verify(periodoService, times(1)).buscarPorId(turmaDTO.getPeriodo());
        verify(semestreService, times(1)).buscarPorId(turmaDTO.getSemestre());
        verify(cursoService, times(1)).buscarPorId(turmaDTO.getCurso());
        verify(turnoService, times(1)).buscarPorId(turmaDTO.getTurno());
        verify(turmaRepository, times(1)).save(any(Turma.class));
    }


    @Test
    void deveTentarEditarUmTurmaComIdInvalidoeRetornarIllegalArgumentException() {
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTOIdInvalido();

        assertThrows(IllegalArgumentException.class, () ->
        {turmaService.editar(turmaDTO.getId(), turmaDTO);});
    }

    @Test
    void deveTentarEditarUmTurmaComNomeNulleRetornarIllegalArgumentException(){
        Long turmaId = 1L;
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
        {turmaService.editar(turmaId, turmaDTO);});

    }

    @Test
    void deveTentarEditarUmTurmaComNomeVazioeRetornarIllegalArgumentException() {
        Long turmaId = 1L;
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () ->
        {turmaService.editar(turmaId, turmaDTO);});
    }


    @Test
    void deveExcluirUmTurmaPorId() {

        Long turmaId = 1L;
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTO1();
        Turma turma = TurmaAdapter.toEntity(turmaDTO);

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turma));

        turmaService.excluir(turmaId);

        verify(turmaRepository, times(1)).delete(turma);
    }

    @Test
    void deveTentarExcluirUmTurmaComIdInvalidoeRetornarRuntimeException() {
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTOIdInvalido();
        assertThrows(RuntimeException.class, () ->
        {turmaService.excluir(turmaDTO.getId());});

    }

    @Test
    void deveBuscarTodosOsTurmasExistentes() {

        TurmaProjection turmaProjection1 = mock(TurmaProjection.class);
        TurmaProjection turmaProjection2 = mock(TurmaProjection.class);

        when(turmaProjection1.getId()).thenReturn(1L);
        when(turmaProjection1.getNome()).thenReturn("Turma A");
        when(turmaProjection2.getId()).thenReturn(2L);
        when(turmaProjection2.getNome()).thenReturn("Turma B");

        List<TurmaProjection> listaTurmas = List.of(turmaProjection1, turmaProjection2);
        when(turmaRepository.findAllTurmasWithPeriodoAndTurnoAndCursoAndSemestre()).thenReturn(listaTurmas);

        var turmas = turmaService.buscarTodasTurmas();

        assertNotNull(turmas, "A lista de turmas não deveria ser nula.");
        assertEquals(2, turmas.size(), "A quantidade de turmas retornada é diferente da esperada.");
        assertEquals(1L, turmas.get(0).getId());
        assertEquals("Turma A", turmas.get(0).getNome());
        assertEquals(2L, turmas.get(1).getId());
        assertEquals("Turma B", turmas.get(1).getNome());
        verify(turmaRepository, times(1)).findAllTurmasWithPeriodoAndTurnoAndCursoAndSemestre();
    }

    @Test
    void deveBuscarTodosAsTurmasEmUmaTabelaVazia() {
        List<TurmaProjection> turmasEncontrados = turmaService.buscarTodasTurmas
                ();
        assertNotNull(turmasEncontrados);
        assertTrue(turmasEncontrados.isEmpty());
    }
}