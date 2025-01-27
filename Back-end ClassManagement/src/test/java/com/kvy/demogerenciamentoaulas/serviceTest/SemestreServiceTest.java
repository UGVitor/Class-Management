package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.Adapter.SemestreAdapter;
import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.exception.SemestreEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.SemestreDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.SemestreRepository;
import com.kvy.demogerenciamentoaulas.service.SemestreService;
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
class SemestreServiceTest {

    @Mock
    private SemestreRepository semestreRepository;

    @InjectMocks
    private SemestreService semestreService;


    @Test
    void deveSalvarUmSemestreValido() {
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTO1();
        Semestre semestreEsperado = SemestreAdapter.toEntity(semestreDTO);

        when(semestreRepository.save(Mockito.any(Semestre.class))).thenReturn(semestreEsperado);

        Semestre semestreSalvo = semestreService.salvar(semestreDTO);

        assertEquals(semestreEsperado.getSemestre(), semestreSalvo.getSemestre());
    }


    @Test
    void deveTentarSalvarUmSemestreComNomeNulleRetornarIllegalArgumentException() {
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
                semestreService.salvar(semestreDTO));

    }

    @Test
    void deveTentarSalvarUmSemestreComNomeVazioeRetornarIllegalArgumentException() {
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () ->
                semestreService.salvar(semestreDTO));
    }


    @Test
    void deveBuscaroSemestrePorId() {
        Long semestreId = 1L;
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTO1();
        Semestre semestre= SemestreAdapter.toEntity(semestreDTO);

        when(semestreRepository.findById(semestreId)).thenReturn(Optional.of(semestre));

        Semestre semestreEncontrado = semestreService.buscarPorId(semestreId);

        assertNotNull(semestreEncontrado);
        assertEquals(semestreId, semestreEncontrado.getId());

    }

    @Test
    void deveTentarBuscaroSemestrePorIdInexistenteeRetornarSemestreEntityNotFoundException() {
        Long semestreId = 1L;

        assertThrows(SemestreEntityNotFoundException.class, () ->
                semestreService.buscarPorId(semestreId));

    }


    @Test
    void deveEditarUmSemestreComIdValido() {
        Long semestreId = 1L;
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTO1();
        semestreDTO.setSemestre("Tipo Atualizado");

        Semestre semestreAtualizado= SemestreAdapter.toEntity(semestreDTO);

        when(semestreRepository.findById(semestreId)).thenReturn(Optional.of(semestreAtualizado));
        when(semestreRepository.save(Mockito.any(Semestre.class))).thenReturn(semestreAtualizado);

        Semestre semestreEditado = semestreService.editar(semestreId, semestreDTO);

        assertNotNull(semestreEditado);
        assertEquals("Tipo Atualizado", semestreEditado.getSemestre());

    }
    @Test
    void deveTentarEditarUmSemestreComIdInvalidoeRetornarSemestreEntityNotFoundException() {
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTOIdInvalido();

        assertThrows(SemestreEntityNotFoundException.class, () ->
                semestreService.editar(semestreDTO.getId(), semestreDTO));
    }

    @Test
    void deveTentarEditarUmSemestreComNomeNulleRetornarIllegalArgumentException(){
        Long semestreId = 1L;
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTONullName();
        Semestre semestre= SemestreAdapter.toEntity(semestreDTO);

        when(semestreRepository.findById(semestreId)).thenReturn(Optional.of(semestre));

        assertThrows(IllegalArgumentException.class, () ->
                semestreService.editar(semestreId, semestreDTO));

    }

    @Test
    void deveTentarEditarUmSemestreComNomeVazioeRetornarIllegalArgumentException() {
        Long semestreId = 1L;
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTOEmptyName();
        Semestre semestre= SemestreAdapter.toEntity(semestreDTO);

        when(semestreRepository.findById(semestreId)).thenReturn(Optional.of(semestre));

        assertThrows(IllegalArgumentException.class, () ->
                semestreService.editar(semestreId, semestreDTO));

    }


    @Test
    void deveExcluirUmSemestrePorId() {
        Long semestreId = 2L;
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTO2();
        Semestre semestre= SemestreAdapter.toEntity(semestreDTO);

        when(semestreRepository.findById(semestreId)).thenReturn(Optional.of(semestre));

        semestreService.excluir(semestreId);

        verify(semestreRepository,times(1)).delete(semestre);
    }

    @Test
    void deveTentarExcluirUmSemestreValidoeRetornarRuntimeException() {
        SemestreDTO semestreDTO = SemestreDTOFixture.fixtureSemestreDTOIdInvalido();
        assertThrows(RuntimeException.class, () ->
                semestreService.excluir(semestreDTO.getId()));

    }

    @Test
    void deveBuscarTodosOsSemestreExistentes() {
        SemestreDTO semestreDTO1 = SemestreDTOFixture.fixtureSemestreDTO1();
        SemestreDTO semestreDTO2 = SemestreDTOFixture.fixtureSemestreDTO2();
        Semestre semestre1 = SemestreAdapter.toEntity(semestreDTO1);
        Semestre semestre2 = SemestreAdapter.toEntity(semestreDTO2);

        when(semestreRepository.findAll()).thenReturn(Arrays.asList(semestre1, semestre2));
        var semestres = semestreService.buscarTodos();

        assertNotNull(semestres);
        assertEquals(2, semestres.size());
    }

    @Test
    void deveBuscarTodosOsSemestreEmUmaTabelaVazia() {

        when(semestreRepository.findAll()).thenReturn(Collections.emptyList());
        List<Semestre> semestresEncontrados = semestreService.buscarTodos();

        assertNotNull(semestresEncontrados);
        assertTrue(semestresEncontrados.isEmpty());

    }
}