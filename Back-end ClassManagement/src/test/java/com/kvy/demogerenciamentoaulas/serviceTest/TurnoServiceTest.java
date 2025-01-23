package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.Adapter.TurnoAdapter;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.exception.TurnoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.TurnoDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.TurnoRepository;
import com.kvy.demogerenciamentoaulas.service.TurnoService;
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
class TurnoServiceTest {


        @Mock
        private TurnoRepository turnoRepository;

        @InjectMocks
        private TurnoService turnoService;

    @Test
    void deveSalvarTurnoValido() {
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTO1();
        Turno turnoEsperado = TurnoAdapter.toEntity(turnoDTO);

        when(turnoRepository.save(Mockito.any(Turno.class))).thenReturn(turnoEsperado);

        Turno turnoSalvo = turnoService.salvar(turnoDTO);

        assertEquals(turnoEsperado.getTurno(), turnoSalvo.getTurno());
    }


    @Test
    void deveTentarSalvarUmTurnoComNomeNull() {
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTONullName();

        assertThrows(IllegalArgumentException.class, () -> {
            turnoService.salvar(turnoDTO);
        });
    }

    @Test
    void deveTentarSalvarUmTurnoComNomeVazio() {
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () -> {
            turnoService.salvar(turnoDTO);
        });
    }


    @Test
    void deveBuscaroTurnoPorId() {
        Long turnoId = 1L;
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTO1();
        Turno turno = TurnoAdapter.toEntity(turnoDTO);

        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turno));

        Turno turnoEncontrado = turnoService.buscarPorId(turnoId);

        assertNotNull(turnoEncontrado);
        assertEquals(turnoId, turnoEncontrado.getId());
    }

    @Test
    void deveBuscaroTurnoPorIdInexistente() {
        Long turnoId = 1L;

        assertThrows(TurnoEntityNotFoundException.class, () ->
        {turnoService.buscarPorId(turnoId);});
    }


    @Test
    void deveEditarUmTurnoComIdValido() {

        Long turnoId = 1L;
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTO1(); // Usando o fixture para o DTO inicial
        turnoDTO.setTurno("Turno Atualizado");

        Turno turnoAtualizado = TurnoAdapter.toEntity(turnoDTO);


        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turnoAtualizado));
        when(turnoRepository.save(Mockito.any(Turno.class))).thenReturn(turnoAtualizado);


        Turno turnoEditado = turnoService.editar(turnoId, turnoDTO);

        assertNotNull(turnoEditado);
        assertEquals("Turno Atualizado", turnoEditado.getTurno());
    }
    @Test
    void deveTentarEditarUmTurnoComIdInvalido() {
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTOIdInvalido();

        assertThrows(TurnoEntityNotFoundException.class, () ->
        {turnoService.editar(turnoDTO.getId(), turnoDTO);});
    }

    @Test
    void deveTentarEditarUmTurnoComNomeNull(){
        Long turnoId = 1L;
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTONullName();
        Turno turnoAtualizado = TurnoAdapter.toEntity(turnoDTO);

        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turnoAtualizado));

        assertThrows(IllegalArgumentException.class, () ->
        {turnoService.editar(turnoId, turnoDTO);});

    }

    @Test
    void deveTentarEditarUmTurnoComNomeVazio() {
        Long turnoId = 1L;
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTOEmptyName();
        Turno turnoAtualizado = TurnoAdapter.toEntity(turnoDTO);

        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turnoAtualizado));

        assertThrows(IllegalArgumentException.class, () ->
        {turnoService.editar(turnoId, turnoDTO);});
    }


    @Test
    void deveExcluirUmTurnoPorId() {

        Long turnoId = 1L;
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTO1();
        Turno turno = TurnoAdapter.toEntity(turnoDTO);

        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turno));

        turnoService.excluir(turnoId);

        verify(turnoRepository, times(1)).delete(turno);
    }

    @Test
    void deveTentarExcluirUmTurnoComIdInvalido() {
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTOIdInvalido();
        assertThrows(RuntimeException.class, () ->
        {turnoService.excluir(turnoDTO.getId());});

    }

    @Test
    void deveBuscarTodosOsTurnosExistentes() {

        TurnoDTO turnoDTO1 = TurnoDTOFixture.fixtureTurnoDTO1();
        TurnoDTO turnoDTO2 = TurnoDTOFixture.fixtureTurnoDTO2();
        Turno turno1 = TurnoAdapter.toEntity(turnoDTO1);
        Turno turno2 = TurnoAdapter.toEntity(turnoDTO2);

        when(turnoRepository.findAll()).thenReturn(Arrays.asList(turno1, turno2));
        var turnos = turnoService.buscarTodos();

        assertNotNull(turnos);
        assertEquals(2, turnos.size());
    }

    @Test
    void deveBuscarTodosOsTurnosEmUmaTabelaVazia() {

        when(turnoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Turno> turnosEncontrados = turnoService.buscarTodos();


        assertNotNull(turnosEncontrados);
    }
    }