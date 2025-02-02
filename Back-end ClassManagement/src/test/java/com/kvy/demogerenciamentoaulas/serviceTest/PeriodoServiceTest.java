package com.kvy.demogerenciamentoaulas.serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import com.kvy.demogerenciamentoaulas.Adapter.PeriodoAdapter;
import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.exception.PeriodoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.PeriodoDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.PeriodoRepository;
import com.kvy.demogerenciamentoaulas.service.PeriodoService;
import com.kvy.demogerenciamentoaulas.web.dto.PeriodoDTO;
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
class PeriodoServiceTest {

    @Mock
    private PeriodoRepository periodoRepository;

    @InjectMocks
    private PeriodoService periodoService;

    @Test
    void deveSalvarUmPeriodoValido() {
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTO1();
        Periodo periodoEsperado = PeriodoAdapter.toEntity(periodoDTO);

        when(periodoRepository.save(Mockito.any(Periodo.class))).thenReturn(periodoEsperado);

        Periodo periodoSalvo = periodoService.salvar(periodoDTO);

        assertEquals(periodoEsperado, periodoSalvo);
    }

    @Test
    void deveTentarSalvarUmPeriodoComNomeNulleRetornarIllegalArgumentException() {
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                periodoService.salvar(periodoDTO));
    }

    @Test
    void deveTentarSalvarUmPeriodoComNomeVazioeRetornarIllegalArgumentException() {
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () ->
                periodoService.salvar(periodoDTO));
    }

    @Test
    void deveBuscarUmPeriodoPorId(){
        Long periodoId = 1L;
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTO1();
        Periodo periodo = PeriodoAdapter.toEntity(periodoDTO);

        when(periodoRepository.findById(periodoId)).thenReturn(Optional.of(periodo));

        Periodo periodoSalvo = periodoService.buscarPorId(periodoId);

        assertNotNull(periodoSalvo);
        assertEquals(periodoId, periodoSalvo.getId());
    }

    @Test
    void deveTentarBuscarUmPeriodoPorIdInexistenteeRetornarPeriodoEntityNotFoundException() {
        Long periodoId = 1L;

        assertThrows(PeriodoEntityNotFoundException.class, () ->
                periodoService.buscarPorId(periodoId));
    }

    @Test
    void deveEditarUmPeriodoComIdValido() {
        Long periodoId = 1L;
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTO1();
        periodoDTO.setNome("Periodo Atualizado");
        Periodo periodo = PeriodoAdapter.toEntity(periodoDTO);

        when(periodoRepository.findById(periodoId)).thenReturn(Optional.of(periodo));
        when(periodoRepository.save(Mockito.any(Periodo.class))).thenReturn(periodo);

        Periodo periodoEditado = periodoService.editar(periodoId, periodoDTO);

        assertNotNull(periodoEditado);
        assertEquals("Periodo Atualizado", periodoEditado.getNome());
    }

    @Test
    void deveTentarEditarUmPeriodoComIdInvalidoeRetornarPeriodoEntityNotFoundException() {
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTOIdInvalido();

        assertThrows(PeriodoEntityNotFoundException.class, () ->
                periodoService.editar(periodoDTO.getId(), periodoDTO));

    }

    @Test
    void deveTentarEditarUmPeriodoComNomeNulleRetornarIllegalArgumentException() {
        Long periodoId = 1L;
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                periodoService.editar(periodoId, periodoDTO));
    }

    @Test
    void deveTentarEditarUmPeriodoComNomeVazioeRetornarIllegalArgumentException() {
        Long periodoId = 1L;
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTOEmptyName();


        assertThrows(IllegalArgumentException.class, () ->
                periodoService.editar(periodoId, periodoDTO));
    }

    @Test
    void deveExcluirUmPeriodoComIdValido() {
        Long periodoId = 1L;
        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTO1();
        Periodo periodo = PeriodoAdapter.toEntity(periodoDTO);

        when(periodoRepository.findById(periodoId)).thenReturn(Optional.of(periodo));
        periodoService.excluir(periodoId);
        verify(periodoRepository, times(1)).delete(periodo);
    }

    @Test
    void deveBuscarTodosOsPeriodosExistentes(){

        PeriodoDTO periodoDTO = PeriodoDTOFixture.fixturePeriodoDTO1();
        PeriodoDTO periodoDTO2 = PeriodoDTOFixture.fixturePeriodoDTO2();
        Periodo periodo1 = PeriodoAdapter.toEntity(periodoDTO);
        Periodo periodo2 = PeriodoAdapter.toEntity(periodoDTO2);

        when(periodoRepository.findAll()).thenReturn(Arrays.asList(periodo1, periodo2));
        var periodos = periodoService.buscarTodos();

        assertNotNull(periodos);
        assertEquals(2, periodos.size());
    }

    @Test
    void deveBuscarTodosOsTurnosEmUmaTabelaVazia() {
        when(periodoRepository.findAll()).thenReturn(Collections.emptyList());
        List<Periodo> periodosEncontrados = periodoService.buscarTodos();
        assertNotNull(periodosEncontrados);
        assertTrue(periodosEncontrados.isEmpty());
    }
}