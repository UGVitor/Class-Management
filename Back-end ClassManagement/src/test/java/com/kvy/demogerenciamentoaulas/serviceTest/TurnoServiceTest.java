package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.Adapter.TurnoAdapter;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.fixtures.TurnoDTOFixture;
import com.kvy.demogerenciamentoaulas.fixtures.TurnoFixture;
import com.kvy.demogerenciamentoaulas.repository.AulaRepository;
import com.kvy.demogerenciamentoaulas.repository.TurnoRepository;
import com.kvy.demogerenciamentoaulas.service.AulaService;
import com.kvy.demogerenciamentoaulas.service.TurnoService;
import com.kvy.demogerenciamentoaulas.web.dto.TurnoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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
    void deveSalvarTurno() {
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTO1();

        Turno turnoEsperado = TurnoAdapter.toEntity(turnoDTO);

        when(turnoRepository.save(Mockito.any(Turno.class))).thenReturn(turnoEsperado);

        Turno turnoSalvo = turnoService.salvar(turnoDTO);

        assertEquals(turnoEsperado.getTurno(), turnoSalvo.getTurno());

    }
    @Test
    void deveBuscaroTurnoPorId() {
        // Arrange
        Long turnoId = 1L;
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTO1();
        Turno turno = TurnoAdapter.toEntity(turnoDTO);

        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turno));

        Turno turnoEncontrado = turnoService.buscarPorId(turnoId);

        assertNotNull(turnoEncontrado);
        assertEquals(turnoId, turnoEncontrado.getId());
    }

    @Test
    void deveEditaroTurno() {

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
    void deveExcluiroTurno() {
        // Arrange
        Long turnoId = 1L;
        TurnoDTO turnoDTO = TurnoDTOFixture.fixtureTurnoDTO1();
        Turno turno = TurnoAdapter.toEntity(turnoDTO);

        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turno));

        // Act
        turnoService.excluir(turnoId);

        // Assert
        verify(turnoRepository, times(1)).delete(turno);
    }

    @Test
    void deveBuscarTodososTurno() {
        // Arrange
        TurnoDTO turnoDTO1 = TurnoDTOFixture.fixtureTurnoDTO1();
        TurnoDTO turnoDTO2 = TurnoDTOFixture.fixtureTurnoDTO2();
        Turno turno1 = TurnoAdapter.toEntity(turnoDTO1);
        Turno turno2 = TurnoAdapter.toEntity(turnoDTO2);

        when(turnoRepository.findAll()).thenReturn(Arrays.asList(turno1, turno2));

        // Act
        var turnos = turnoService.buscarTodos();

        // Assert
        assertNotNull(turnos);
        assertEquals(2, turnos.size());
    }
}