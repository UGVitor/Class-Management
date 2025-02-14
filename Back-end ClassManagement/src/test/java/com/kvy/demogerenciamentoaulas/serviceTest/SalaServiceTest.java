package com.kvy.demogerenciamentoaulas.serviceTest;

import java.util.List;
import java.util.Optional;

import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.exception.SalaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.Projection.SalaProjection;
import com.kvy.demogerenciamentoaulas.repository.TipoSalaRepository;
import com.kvy.demogerenciamentoaulas.service.SalaService;
import com.kvy.demogerenciamentoaulas.entity.Sala;
import com.kvy.demogerenciamentoaulas.fixtures.SalaDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.SalaRepository;
import com.kvy.demogerenciamentoaulas.service.TipoSalaService;
import com.kvy.demogerenciamentoaulas.web.dto.SalaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;
    @Mock
    private TipoSalaRepository tiposalaRepository;
    @Mock
    private TipoSalaService tipoSalaService;
    @InjectMocks
    private SalaService salaService;


    @Test
    void deveSalvarUmaSala() {
        SalaDTO salaDTO = new SalaDTO();
        salaDTO.setCapacidade(12);
        salaDTO.setNumero(10);
        salaDTO.setTipoSala(1L);
        salaDTO.setId(1L);

        TipoSala tipoSala = new TipoSala();
        tipoSala.setId(1L);

        when(tipoSalaService.buscarPorId(anyLong())).thenReturn(tipoSala);

        when(salaRepository.save(any(Sala.class))).thenAnswer(invocation -> {
            Sala sala = invocation.getArgument(0);
            sala.setId(1L);
            return sala;
        });

        Sala salaSalva = salaService.salvar(salaDTO);

        assertNotNull(salaSalva);
        assertEquals(salaDTO.getNumero(), salaSalva.getNumero());
        assertEquals(salaDTO.getCapacidade(), salaSalva.getCapacidade());
        assertEquals(tipoSala.getId(), salaSalva.getTipoSala().getId());

        verify(tipoSalaService, times(1)).buscarPorId(anyLong());
        verify(salaRepository, times(1)).save(any(Sala.class));
    }


    @Test
    void deveBuscarUmaSalaPorId() {
        Sala sala = new Sala();
        sala.setId(SalaDTO.getSalaId());

        SalaDTO salaDTO = SalaDTOFixture.fixtureLaboratorio();
        sala.setId(salaDTO.getId());
    }

    @Test
    void deveEditarUmaSala() {
        Long salaId = 2L;

        SalaDTO salaDTOAtualizada = new SalaDTO();
        salaDTOAtualizada.setNumero(15);
        salaDTOAtualizada.setCapacidade(20);
        salaDTOAtualizada.setTipoSala(1L);
        salaDTOAtualizada.setId(1L);

        TipoSala tipoSala = new TipoSala();
        tipoSala.setId(1L);

        Sala salaExistente = new Sala();
        salaExistente.setId(salaId);
        salaExistente.setNumero(10);
        salaExistente.setCapacidade(12);
        salaExistente.setTipoSala(tipoSala);

        when(salaRepository.findById(salaId)).thenReturn(Optional.of(salaExistente));

        when(tipoSalaService.buscarPorId(anyLong())).thenReturn(tipoSala);

        when(salaRepository.save(any(Sala.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Sala salaEditada = salaService.editar(salaId, salaDTOAtualizada);

        assertNotNull(salaEditada);
        assertEquals(salaId, salaEditada.getId());
        assertEquals(salaDTOAtualizada.getNumero(), salaEditada.getNumero());
        assertEquals(salaDTOAtualizada.getCapacidade(), salaEditada.getCapacidade());
        assertEquals(salaDTOAtualizada.getTipoSala(), salaEditada.getTipoSala().getId());

        verify(salaRepository, times(1)).findById(salaId);
        verify(tipoSalaService, times(1)).buscarPorId(anyLong());
        verify(salaRepository, times(1)).save(any(Sala.class));
    }


    @Test
    void deveExcluirUmaSala() {
        Long salaId = 1L;
        Sala sala = new Sala();
        sala.setId(salaId);

        when(salaRepository.findById(salaId)).thenReturn(Optional.of(sala));
        doNothing().when(salaRepository).delete(sala);

        assertDoesNotThrow(() -> salaService.excluir(salaId));

        verify(salaRepository, times(1)).findById(salaId);
        verify(salaRepository, times(1)).delete(sala);
    }


    @Test
    void deveBuscarTodasAsSalas() {
        SalaProjection salaProjection = mock(SalaProjection.class);
        when(salaRepository.findAllSalas()).thenReturn(List.of(salaProjection));

        List<SalaProjection> salasEncontradas = salaService.buscarTodasSalas();

        assertNotNull(salasEncontradas);
        assertEquals(1, salasEncontradas.size());
    }


    @Test
    void deveLancarExcecaoQuandoSalaIdEhNulo() {
        SalaDTO salaDTO = SalaDTOFixture.fixtureLaboratorio();
        salaDTO.setId(null);

        assertThrows(IllegalArgumentException.class, () -> salaService.salvar(salaDTO), "Expected IllegalArgumentException was not thrown");
    }

    @Test
    void deveLancarExcecaoQuandoSalaNaoEncontrada() {
        when(salaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SalaEntityNotFoundException.class, () -> salaService.buscarPorId(1L));
    }

    @Test
    void deveTentarBuscarUmaSalaComIdInvalido() {
        Long invalidId = -1L;

        when(salaRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(SalaEntityNotFoundException.class, () -> salaService.buscarPorId(invalidId));

        verify(salaRepository, times(1)).findById(invalidId);

    }

    @Test
    void deveTentarEditarUmaSalaComIdInvalido() {
        Long invalidId = -1L;

        SalaDTO salaDTO = new SalaDTO();
        salaDTO.setNumero(15);
        salaDTO.setCapacidade(20);
        salaDTO.setTipoSala(1L);
        salaDTO.setId(1L);

        when(salaRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(SalaEntityNotFoundException.class, () -> salaService.editar(invalidId, salaDTO));

        verify(salaRepository, times(1)).findById(invalidId);
    }


    @Test
    void deveTentarExcluirUmaSalaComIdInvalido() {
        Long invalidId = -1L;

        when(salaRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(SalaEntityNotFoundException.class, () -> salaService.excluir(invalidId));

        verify(salaRepository, times(1)).findById(invalidId);
    }

}