package com.kvy.demogerenciamentoaulas.serviceTest;
import com.kvy.demogerenciamentoaulas.Adapter.DiaSemanaAdapter;
import com.kvy.demogerenciamentoaulas.Adapter.SemestreAdapter;
import com.kvy.demogerenciamentoaulas.entity.DiaSemana;
import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.exception.DiaSemanaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.SemestreEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.DiaSemanaDTOFixture;
import com.kvy.demogerenciamentoaulas.fixtures.SemestreDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.DiaSemanaRepository;

import com.kvy.demogerenciamentoaulas.service.DiaSemanaService;

import com.kvy.demogerenciamentoaulas.web.dto.DiaSemanaDTO;
import com.kvy.demogerenciamentoaulas.web.dto.SemestreDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

class DiaSemanaServiceTest {

    @Mock
    private DiaSemanaRepository diaSemanaRepository;

    @InjectMocks
    private DiaSemanaService diaSemanaService;


    @Test
    void deveSalvarUmDiaSemanaValido() {
        DiaSemanaDTO diaSemanaDTO = DiaSemanaDTOFixture.fixtureDiaSemanaDTO1();
        DiaSemana diaSemanaEsperado = DiaSemanaAdapter.toEntity(diaSemanaDTO);

        when(diaSemanaRepository.save(Mockito.any(DiaSemana.class))).thenReturn(diaSemanaEsperado);

        DiaSemana diaSemanaSalvo = diaSemanaService.salvar(diaSemanaDTO);

        assertEquals(diaSemanaEsperado.getDia(), diaSemanaSalvo.getDia());
    }


    @Test
    void deveTentarSalvarUmDiaSemanaComNomeNull() {
        DiaSemanaDTO diaSemanaDTO = DiaSemanaDTOFixture.fixtureDiaSemanaDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                diaSemanaService.salvar(diaSemanaDTO));

    }

    @Test
    void deveTentarSalvarUmDiaSemanaComNomeVazio() {
        DiaSemanaDTO diaSemanaDTO = DiaSemanaDTOFixture.fixtureDiaSemanaDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () ->
                diaSemanaService.salvar(diaSemanaDTO));
    }


    @Test
    void deveBuscaroDiaSemanaPorId() {
        Long diaSemanaId = 1L;
        DiaSemanaDTO diaSemanaDTO = DiaSemanaDTOFixture.fixtureDiaSemanaDTO1();
        DiaSemana diaSemana= DiaSemanaAdapter.toEntity(diaSemanaDTO);

        when(diaSemanaRepository.findById(diaSemanaId)).thenReturn(Optional.of(diaSemana));

        DiaSemana diaSemanaEncontrado = diaSemanaService.buscarPorId(diaSemanaId);

        assertNotNull(diaSemanaEncontrado);
        assertEquals(diaSemanaId, diaSemanaEncontrado.getId());

    }

    @Test
    void deveBuscaroDiaSemanaPorIdInexistente() {
        Long disSemanaId = 1L;

        assertThrows(DiaSemanaEntityNotFoundException.class, () ->
                diaSemanaService.buscarPorId(disSemanaId));

    }


    @Test
    void deveEditarUmDiaSemanaComIdValido() {
        Long diaSemanaId = 1L;
        DiaSemanaDTO diaSemanaDTO = DiaSemanaDTOFixture.fixtureDiaSemanaDTO1();
        diaSemanaDTO.setDia("Tipo Atualizado");

        DiaSemana diaSemanaAtualizado= DiaSemanaAdapter.toEntity(diaSemanaDTO);

            when(diaSemanaRepository.findById(diaSemanaId)).thenReturn(Optional.of(diaSemanaAtualizado));
            when(diaSemanaRepository.save(Mockito.any(DiaSemana.class))).thenReturn(diaSemanaAtualizado);

        DiaSemana diaSemanaIdEditado = diaSemanaService.editar(diaSemanaId, diaSemanaDTO);

            assertNotNull(diaSemanaIdEditado);
            assertEquals("Tipo Atualizado", diaSemanaIdEditado.getDia());


    }
    @Test
    void deveTentarEditarUmDiaSemanaComIdInvalidoERetornarIllegalArgumentException() {
        DiaSemanaDTO diaSemanaDTO =  DiaSemanaDTOFixture.fixtureDiaSemanaDTOIdInvalido();

        assertThrows(IllegalArgumentException.class, () ->
                diaSemanaService.editar(diaSemanaDTO.getId(), diaSemanaDTO));
    }

    @Test
    void deveTentarEditarUmDiaSemanaComNomeNull(){
        Long disSemanaId = 1L;
        DiaSemanaDTO diaSemanaDTO =  DiaSemanaDTOFixture.fixtureDiaSemanaDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                diaSemanaService.editar(disSemanaId, diaSemanaDTO));

    }

    @Test
    void deveTentarEditarUmDiaSemanaComNomeVazio() {
        Long disSemanaId = 1L;
        DiaSemanaDTO diaSemanaDTO =  DiaSemanaDTOFixture.fixtureDiaSemanaDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () ->
                diaSemanaService.editar(disSemanaId, diaSemanaDTO));

    }


    @Test
    void deveExcluirUmDiaSemanaPorId() {
        Long disSemanaId = 2L;
        DiaSemanaDTO diaSemanaDTO = DiaSemanaDTOFixture.fixtureDiaSemanaDTO2();
        DiaSemana diaSemana= DiaSemanaAdapter.toEntity(diaSemanaDTO);

        when(diaSemanaRepository.findById(disSemanaId)).thenReturn(Optional.of(diaSemana));

        diaSemanaService.excluir(disSemanaId);

        verify(diaSemanaRepository,times(1)).delete(diaSemana);
    }

    @Test
    void deveTentarExcluirUmDiaSemanaValido() {
        DiaSemanaDTO diaSemanaDTO = DiaSemanaDTOFixture.fixtureDiaSemanaDTOIdInvalido();
        assertThrows(RuntimeException.class, () ->
                diaSemanaService.excluir(diaSemanaDTO.getId()));

    }

    @Test
    void deveBuscarTodosOsTiposDeSalasExistentes() {
        DiaSemanaDTO diaSemanaDTO1 = DiaSemanaDTOFixture.fixtureDiaSemanaDTO1();
        DiaSemanaDTO diaSemanaDTO2 = DiaSemanaDTOFixture.fixtureDiaSemanaDTO2();
        DiaSemana dia1 = DiaSemanaAdapter.toEntity(diaSemanaDTO1);
        DiaSemana dia2 = DiaSemanaAdapter.toEntity(diaSemanaDTO2);

        when(diaSemanaRepository.findAll()).thenReturn(Arrays.asList(dia1, dia2));
        var dias = diaSemanaService.buscarTodos();

        assertNotNull(dias);
        assertEquals(2, dias.size());
    }

    @Test
    void deveBuscarTodosOsDiasEmUmaTabelaVazia() {

        when(diaSemanaRepository.findAll()).thenReturn(Collections.emptyList());
        List<DiaSemana> diaSemanaEncontrados = diaSemanaService.buscarTodos();

        assertNotNull(diaSemanaEncontrados);


    }
}

