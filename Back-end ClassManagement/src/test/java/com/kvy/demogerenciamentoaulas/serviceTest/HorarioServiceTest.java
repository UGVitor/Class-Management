package com.kvy.demogerenciamentoaulas.serviceTest;
import com.kvy.demogerenciamentoaulas.entity.Horario;
import com.kvy.demogerenciamentoaulas.fixtures.HorarioDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.HorarioRepository;
import com.kvy.demogerenciamentoaulas.service.HorarioService;
import com.kvy.demogerenciamentoaulas.web.dto.HorarioDTO;
import com.kvy.demogerenciamentoaulas.exception.HorarioEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class HorarioServiceTest {

    @Mock
    private HorarioRepository horarioRepository;

    @InjectMocks
    private HorarioService horarioService;

    private HorarioDTO horarioDTOValido;
    private HorarioDTO horarioDTOInvalido;
    private HorarioDTO horarioDTOComHoraInicioNula;
    private HorarioDTO horarioDTOComHoraTerminoNula;
    private HorarioDTO horarioDTOSemId;

    @BeforeEach
    void setUp() {
        horarioDTOValido = HorarioDTOFixture.criarHorarioDTOValido();
        horarioDTOInvalido = HorarioDTOFixture.criarHorarioDTOInvalido();
        horarioDTOComHoraInicioNula = HorarioDTOFixture.criarHorarioDTOComHoraInicioNula();
        horarioDTOComHoraTerminoNula = HorarioDTOFixture.criarHorarioDTOComHoraTerminoNula();
        horarioDTOSemId = HorarioDTOFixture.criarHorarioDTOSemId();
    }

    @Test
    void deveSalvarUmHorarioValido() {
        Horario horario = horarioService.convertToEntity(horarioDTOValido);
        when(horarioRepository.save(any(Horario.class))).thenReturn(horario);

        Horario resultado = horarioService.salvar(horarioDTOValido);

        assertNotNull(resultado);
        assertEquals(horarioDTOValido.getHoraInicio(), resultado.getHoraInicio());
        assertEquals(horarioDTOValido.getHoraTermino(), resultado.getHoraTermino());
        verify(horarioRepository, times(1)).save(any(Horario.class));
    }


    @Test
    void deveBuscarUmHorarioPorId() {
        Horario horario = horarioService.convertToEntity(horarioDTOValido);
        when(horarioRepository.findById(1L)).thenReturn(Optional.of(horario));

        Horario resultado = horarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(horarioDTOValido.getHoraInicio(), resultado.getHoraInicio());
        assertEquals(horarioDTOValido.getHoraTermino(), resultado.getHoraTermino());
        verify(horarioRepository, times(1)).findById(1L);
    }

    @Test
    void deveTentarBuscarUmHorarioPorIdInexistenteERetornarHorarioEntityNotFoundException() {
        when(horarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HorarioEntityNotFoundException.class, () -> horarioService.buscarPorId(1L));
    }

    @Test
    void deveEditarUmHorarioComIdValido() {
        Horario horarioExistente = horarioService.convertToEntity(horarioDTOValido);
        when(horarioRepository.findById(1L)).thenReturn(Optional.of(horarioExistente));
        when(horarioRepository.save(any(Horario.class))).thenReturn(horarioExistente);

        Horario resultado = horarioService.editar(1L, horarioExistente);

        assertNotNull(resultado);
        assertEquals(horarioDTOValido.getHoraInicio(), resultado.getHoraInicio());
        assertEquals(horarioDTOValido.getHoraTermino(), resultado.getHoraTermino());
        verify(horarioRepository, times(1)).save(any(Horario.class));
    }

    @Test
    void deveTentarEditarUmHorarioComIdInexistenteERetornarHorarioEntityNotFoundException() {
        when(horarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HorarioEntityNotFoundException.class, () -> horarioService.editar(1L, new Horario()));
    }


    @Test
    void deveExcluirUmHorarioPorId() {
        Horario horario = horarioService.convertToEntity(horarioDTOValido);
        when(horarioRepository.findById(1L)).thenReturn(Optional.of(horario));
        doNothing().when(horarioRepository).delete(horario);

        horarioService.excluir(1L);

        verify(horarioRepository, times(1)).delete(horario);
    }

    @Test
    void deveTentarExcluirUmHorarioComIdInvalidoERetornarRuntimeException() {
        when(horarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HorarioEntityNotFoundException.class, () -> horarioService.excluir(1L));
    }

    @Test
    void deveBuscarTodosOsHorariosExistentes() {
        Horario horario = horarioService.convertToEntity(horarioDTOValido);
        when(horarioRepository.findAll()).thenReturn(List.of(horario));

        List<Horario> resultado = horarioService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(horarioRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarTodosOsHorariosEmUmaTabelaVazia() {
        when(horarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<Horario> resultado = horarioService.buscarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(horarioRepository, times(1)).findAll();
    }
}