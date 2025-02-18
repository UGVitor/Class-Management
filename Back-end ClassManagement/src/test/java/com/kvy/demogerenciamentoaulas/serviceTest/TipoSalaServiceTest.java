package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.Adapter.TipoSalaAdapter;
import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.exception.TipoSalaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.TipoSalaDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.TipoSalaRepository;
import com.kvy.demogerenciamentoaulas.fixtures.TipoSalaDTOFixture;
import com.kvy.demogerenciamentoaulas.service.TipoSalaService;

import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;
import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;
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
class TipoSalaServiceTest {

    @Mock
    private TipoSalaRepository tipoSalaRepository;

    @InjectMocks
    private TipoSalaService tipoSalaService;

    @Test
    void deveSalvarUmTipoSalaValido() {
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTO1();
        TipoSala tipoSalaEsperado = TipoSalaAdapter.toEntity(tipoSalaDTO);

        when(tipoSalaRepository.save(Mockito.any(TipoSala.class))).thenReturn(tipoSalaEsperado);

        TipoSalaDTO tipoSalaSalvo = tipoSalaService.salvar(tipoSalaDTO);

        assertEquals(tipoSalaEsperado.getTipoSala(), tipoSalaSalvo.getTipoSala());
    }


    @Test
    void deveTentarSalvarUmTipoSalaComNomeNulleRetornarIllegalArgumentException() {
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                tipoSalaService.salvar(tipoSalaDTO));

    }

    @Test
    void deveTentarSalvarUmTipoSalaComNomeVazioeRetornarNullPointerException() {
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTOEmptyName();

        assertThrows(NullPointerException.class, () ->
                tipoSalaService.salvar(tipoSalaDTO));
    }


    @Test
    void deveBuscaroTipoSalaPorId() {
        Long tipoSalaId = 1L;
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTO1();
        TipoSala tipoSala= TipoSalaAdapter.toEntity(tipoSalaDTO);

        when(tipoSalaRepository.findById(tipoSalaId)).thenReturn(Optional.of(tipoSala));

        TipoSala tipoSalaEncontrado = tipoSalaService.buscarPorId(tipoSalaId);

        assertNotNull(tipoSalaEncontrado);
        assertEquals(tipoSalaId, tipoSalaEncontrado.getId());

    }

    @Test
    void deveTentarBuscaroTipoSalaPorIdInexistenteeRetornarTipoSalaEntityNotFoundException() {
        Long tipoSalaId = 1L;

        assertThrows(TipoSalaEntityNotFoundException.class, () ->
                tipoSalaService.buscarPorId(tipoSalaId));

    }


    @Test
    void deveEditarUmTipoSalaComIdValido() {
        Long tipoSalaId = 1L;
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTO1();
        tipoSalaDTO.setTipoSala("Tipo Atualizado");

        TipoSala tipoSalaAtualizado= TipoSalaAdapter.toEntity(tipoSalaDTO);

        when(tipoSalaRepository.findById(tipoSalaId)).thenReturn(Optional.of(tipoSalaAtualizado));
        when(tipoSalaRepository.save(Mockito.any(TipoSala.class))).thenReturn(tipoSalaAtualizado);

        TipoSala tipoSalaEditado = tipoSalaService.editar(tipoSalaId, tipoSalaDTO);

        assertNotNull(tipoSalaEditado);
        assertEquals("Tipo Atualizado", tipoSalaEditado.getTipoSala());

    }
    @Test
    void deveTentarEditarUmTipoSalaComIdInvalidoeRetornarTipoSalaEntityNotFoundException() {
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTOIdInvalido();

        assertThrows(TipoSalaEntityNotFoundException.class, () ->
                tipoSalaService.editar(tipoSalaDTO.getId(), tipoSalaDTO));
    }

    @Test
    void deveTentarEditarUmTipoSalaComNomeNulleRetornarIllegalArgumentException(){
        Long tipoSalaId = 1L;
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                tipoSalaService.editar(tipoSalaId, tipoSalaDTO));

    }

    @Test
    void deveTentarEditarUmTipoSalaComNomeVazioeRetornarTipoSalaEntityNotFoundException() {
        Long tipoSalaId = 1L;
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTOEmptyName();

        assertThrows(TipoSalaEntityNotFoundException.class, () ->
                tipoSalaService.editar(tipoSalaId, tipoSalaDTO));

    }


    @Test
    void deveExcluirUmTipoSalaPorId() {
        Long tipoSalaId = 2L;
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTO2();
        TipoSala tipoSala= TipoSalaAdapter.toEntity(tipoSalaDTO);

        when(tipoSalaRepository.findById(tipoSalaId)).thenReturn(Optional.of(tipoSala));

        tipoSalaService.excluir(tipoSalaId);

        verify(tipoSalaRepository,times(1)).delete(tipoSala);
    }

    @Test
    void deveTentarExcluirUmTipoSalaValidoeRetornarRuntimeException() {
        TipoSalaDTO tipoSalaDTO = TipoSalaDTOFixture.fixtureTipoSalaDTOIdInvalido();
        assertThrows(RuntimeException.class, () ->
                tipoSalaService.excluir(tipoSalaDTO.getId()));

    }

    @Test
    void deveBuscarTodosOsTiposDeSalasExistentes() {
        TipoSalaDTO tipoSalaDTO1 = TipoSalaDTOFixture.fixtureTipoSalaDTO1();
        TipoSalaDTO tipoSalaDTO2 = TipoSalaDTOFixture.fixtureTipoSalaDTO2();
        TipoSala tipoSala1 = TipoSalaAdapter.toEntity(tipoSalaDTO1);
        TipoSala tipoSala2 = TipoSalaAdapter.toEntity(tipoSalaDTO2);

        when(tipoSalaRepository.findAll()).thenReturn(Arrays.asList(tipoSala1, tipoSala2));
        var tipoSalas = tipoSalaService.buscarTodos();

        assertNotNull(tipoSalas);
        assertEquals(2, tipoSalas.size());
    }

    @Test
    void deveBuscarTodosOsTiposDeSalasEmUmaTabelaVazia() {

        when(tipoSalaRepository.findAll()).thenReturn(Collections.emptyList());
        List<TipoSala> tipoSalasEncontradas = tipoSalaService.buscarTodos();

        assertNotNull(tipoSalasEncontradas);
        assertTrue(tipoSalasEncontradas.isEmpty());

    }
}