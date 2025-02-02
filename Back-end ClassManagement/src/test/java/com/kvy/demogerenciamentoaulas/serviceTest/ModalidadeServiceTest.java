package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.Adapter.ModalidadeAdapter;
import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.exception.ModalidadeEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.ModalidadeDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.ModalidadeRepository;
import com.kvy.demogerenciamentoaulas.service.ModalidadeService;
import com.kvy.demogerenciamentoaulas.web.dto.ModalidadeDTO;

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
class ModalidadeServiceTest {

    @Mock
    private ModalidadeRepository modalideRepository;

    @InjectMocks
    private ModalidadeService modalideService;


    @Test
    void deveSalvarUmModalidadeValido() {
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTO1();
        Modalidade modalideEsperado = ModalidadeAdapter.toEntity(modalideDTO);

        when(modalideRepository.save(Mockito.any(Modalidade.class))).thenReturn(modalideEsperado);

        Modalidade modalideSalvo = modalideService.salvar(modalideDTO);

        assertEquals(modalideEsperado.getNome(), modalideSalvo.getNome());
    }


    @Test
    void deveTentarSalvarUmModalidadeComNomeNull() {
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                modalideService.salvar(modalideDTO));

    }

    @Test
    void deveTentarSalvarUmModalidadeComNomeVazio() {
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () ->
                modalideService.salvar(modalideDTO));
    }


    @Test
    void deveBuscaroModalidadePorId() {
        Long modalideId = 1L;
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTO1();
        Modalidade modalide= ModalidadeAdapter.toEntity(modalideDTO);

        when(modalideRepository.findById(modalideId)).thenReturn(Optional.of(modalide));

        Modalidade modalideEncontrado = modalideService.buscarPorId(modalideId);

        assertNotNull(modalideEncontrado);
        assertEquals(modalideId, modalideEncontrado.getId());

    }

    @Test
    void deveBuscaroModalidadePorIdInexistente() {
        Long modalideId = 1L;

        assertThrows(ModalidadeEntityNotFoundException.class, () ->
                modalideService.buscarPorId(modalideId));

    }


    @Test
    void deveEditarUmaModalidadeComIdValido() {
        Long modalideId = 1L;
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTO1();
        modalideDTO.setNome("Tipo Atualizado");

        Modalidade modalideAtualizado= ModalidadeAdapter.toEntity(modalideDTO);

        when(modalideRepository.findById(modalideId)).thenReturn(Optional.of(modalideAtualizado));
        when(modalideRepository.save(Mockito.any(Modalidade.class))).thenReturn(modalideAtualizado);

        Modalidade modalideEditado = modalideService.editar(modalideId, modalideDTO);

        assertNotNull(modalideEditado);
        assertEquals("Tipo Atualizado", modalideEditado.getNome());

    }
    @Test
    void deveTentarEditarUmModalidadeComIdInvalido() {
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTOIdInvalido();

        assertThrows(IllegalArgumentException.class, () ->
                modalideService.editar(modalideDTO.getId(), modalideDTO));
    }

    @Test
    void deveTentarEditarUmModalidadeComNomeNull(){
        Long modalideId = 1L;
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                modalideService.editar(modalideId, modalideDTO));

    }

    @Test
    void deveTentarEditarUmModalidadeComNomeVazio() {
        Long modalideId = 1L;
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () ->
                modalideService.editar(modalideId, modalideDTO));

    }


    @Test
    void deveExcluirUmModalidadePorId() {
        Long modalideId = 2L;
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTO2();
        Modalidade modalide= ModalidadeAdapter.toEntity(modalideDTO);

        when(modalideRepository.findById(modalideId)).thenReturn(Optional.of(modalide));

        modalideService.excluir(modalideId);

        verify(modalideRepository,times(1)).delete(modalide);
    }

    @Test
    void deveTentarExcluirUmModalidadeValido() {
        ModalidadeDTO modalideDTO = ModalidadeDTOFixture.fixtureModalidadeDTOIdInvalido();
        assertThrows(RuntimeException.class, () ->
                modalideService.excluir(modalideDTO.getId()));

    }

    @Test
    void deveBuscarTodosAsModalidadesExistentes() {
        ModalidadeDTO modalideDTO1 = ModalidadeDTOFixture.fixtureModalidadeDTO1();
        ModalidadeDTO modalideDTO2 = ModalidadeDTOFixture.fixtureModalidadeDTO2();
        Modalidade modalide1 = ModalidadeAdapter.toEntity(modalideDTO1);
        Modalidade modalide2 = ModalidadeAdapter.toEntity(modalideDTO2);

        when(modalideRepository.findAll()).thenReturn(Arrays.asList(modalide1, modalide2));
        var modalidades = modalideService.buscarTodos();

        assertNotNull(modalidades);
        assertEquals(2, modalidades.size());
    }

    @Test
    void deveBuscarTodosAsModalidadeEmUmaTabelaVazia() {

        when(modalideRepository.findAll()).thenReturn(Collections.emptyList());
        List<Modalidade> modalidesEncontradas = modalideService.buscarTodos();

        assertNotNull(modalidesEncontradas);


    }
}
