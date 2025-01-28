package com.kvy.demogerenciamentoaulas.serviceTest;
import com.kvy.demogerenciamentoaulas.Adapter.TurmaAdapter;
import com.kvy.demogerenciamentoaulas.Adapter.TurnoAdapter;
import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.exception.TurmaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.TurnoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.TurmaDTOFixture;
import com.kvy.demogerenciamentoaulas.fixtures.TurnoDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.Projection.TurmaProjection;
import com.kvy.demogerenciamentoaulas.repository.TurmaRepository;
import com.kvy.demogerenciamentoaulas.repository.TurnoRepository;
import com.kvy.demogerenciamentoaulas.service.TurmaService;
import com.kvy.demogerenciamentoaulas.service.TurnoService;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;
import com.kvy.demogerenciamentoaulas.web.dto.TurnoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @InjectMocks
    private TurmaService turmaService;

    @Test
    void deveSalvarUmTurmaValido() {
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTO1();
        Turma turmaEsperado = TurmaAdapter.toEntity(turmaDTO);

        when(turmaRepository.save(Mockito.any(Turma.class))).thenReturn(turmaEsperado);

        Turma turmaSalvo = turmaService.salvar(turmaDTO);

        assertEquals(turmaEsperado.getTurno(), turmaSalvo.getNome());
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
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTO1(); // Usando o fixture para o DTO inicial
        turmaDTO.setNome("Turma Atualizado");

        Turma turmaAtualizado = TurmaAdapter.toEntity(turmaDTO);


        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turmaAtualizado));
        when(turmaRepository.save(Mockito.any(Turma.class))).thenReturn(turmaAtualizado);


        Turma turmaEditado = turmaService.editar(turmaId, turmaDTO);

        assertNotNull(turmaEditado);
        assertEquals("Turma Atualizado", turmaEditado.getTurno());
    }
    @Test
    void deveTentarEditarUmTurmaComIdInvalidoeRetornarTurnoEntityNotFoundException() {
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTOIdInvalido();

        assertThrows(TurmaEntityNotFoundException.class, () ->
        {turmaService.editar(turmaDTO.getId(), turmaDTO);});
    }

    @Test
    void deveTentarEditarUmTurmaComNomeNulleRetornarIllegalArgumentException(){
        Long turmaId = 1L;
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTONullName();
        Turma turmaAtualizado = TurmaAdapter.toEntity(turmaDTO);

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turmaAtualizado));

        assertThrows(IllegalArgumentException.class, () ->
        {turmaService.editar(turmaId, turmaDTO);});

    }

    @Test
    void deveTentarEditarUmTurmaComNomeVazioeRetornarIllegalArgumentException() {
        Long turmaId = 1L;
        TurmaDTO turmaDTO = TurmaDTOFixture.fixtureTurmaDTOEmptyName();
        Turma turmaAtualizado = TurmaAdapter.toEntity(turmaDTO);

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turmaAtualizado));

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

        TurmaDTO turmaDTO1 = TurmaDTOFixture.fixtureTurmaDTO1();
        TurmaDTO turmaDTO2 = TurmaDTOFixture.fixtureTurmaDTO2();
        Turma turma1 = TurmaAdapter.toEntity(turmaDTO1);
        Turma turma2 = TurmaAdapter.toEntity(turmaDTO2);

        when(turmaRepository.findAll()).thenReturn(Arrays.asList(turma1, turma2));
        var turmas = turmaService.buscarTodasTurmasComDetalhes();

        assertNotNull(turmas);
        assertEquals(2, turmas.size());
    }

    @Test
    void deveBuscarTodosAsTurmasEmUmaTabelaVazia() {

        when(turmaRepository.findAll()).thenReturn(Collections.emptyList());
        List<TurmaProjection> turmasEncontrados = turmaService.buscarTodasTurmasComDetalhes();
        assertNotNull(turmasEncontrados);
        assertTrue(turmasEncontrados.isEmpty());
    }
}