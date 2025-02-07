package com.kvy.demogerenciamentoaulas.serviceTest;


import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.PerfilEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.PerfilUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.PerfilRepository;
import com.kvy.demogerenciamentoaulas.service.PerfilService;
import com.kvy.demogerenciamentoaulas.web.dto.PerfilDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Nested
@ExtendWith(MockitoExtension.class)
class PerfilServiceTest {


    @Mock
    private PerfilRepository perfilRepository;

    @InjectMocks
    private PerfilService perfilService;

    @Test
    void deveSalvarUmPerfilComIdValido() {
        PerfilDTO perfilDTO = new PerfilDTO(null, "PROFESSOR");
        Perfil perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNome("PROFESSOR");
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);

        Perfil salvo = perfilService.salvar(perfilDTO);

        assertNotNull(salvo);
        assertEquals("PROFESSOR", salvo.getNome());
    }

    @Test
    void deveTentarSalvarUmPerfilComNomeNulleRetornarIllegalArgumentException() {
        PerfilDTO perfilDTO = new PerfilDTO(null, null);

        assertThrows(IllegalArgumentException.class, () -> perfilService.salvar(perfilDTO));
    }

    @Test
    void deveTentarSalvarUmPerfilComNomeVazioeRetornarIllegalArgumentException() {
        PerfilDTO perfilDTO = new PerfilDTO(null, "");

        assertThrows(IllegalArgumentException.class, () -> perfilService.salvar(perfilDTO));
    }

    @Test
    void deveBuscarUmPerfilPorIdValido() {
        Perfil perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNome("PROFESSOR");
        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));

        Perfil encontrado = perfilService.buscarPorId(1L);

        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        assertEquals("PROFESSOR", encontrado.getNome());
    }


    @Test
    void deveTentarBuscarUmPerfilPorIdInexistenteeRetornarPerfilEntityNotFoundException() {
        when(perfilRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PerfilEntityNotFoundException.class, () -> perfilService.buscarPorId(1L));
    }

    @Test
    void deveEditarUmPerfilComIdValido() {
        Perfil perfilExistente = new Perfil(1L, "PROFESSOR");
        Perfil perfilEditado = new Perfil();
        perfilEditado.setNome("ADMIN");

        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfilExistente));
        when(perfilRepository.save(any(Perfil.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Perfil atualizado = perfilService.editar(1L, perfilEditado);

        assertNotNull(atualizado);
        assertEquals("Admin", atualizado.getNome());
    }


    @Test
    void deveTentarEditarUmPerfilComIdInvalidoeRetornarPerfilIllegalArgumentException() {
        Perfil perfilEditado = new Perfil(1L, "ADMIN");

        assertThrows(IllegalArgumentException.class, () -> perfilService.editar(1L, perfilEditado));
    }

    @Test
    void deveTentarEditarUmPerfilComNomeNulleRetornarIllegalArgumentException() {
        Perfil perfilEditado = new Perfil(1L, null);

        assertThrows(IllegalArgumentException.class, () -> perfilService.editar(1L, perfilEditado));
    }

    @Test
    void deveTentarEditarUmPerfilComNomeVazioeRetornarIllegalArgumentException() {
        Perfil perfilEditado = new Perfil(1L, "");

        assertThrows(IllegalArgumentException.class, () -> perfilService.editar(1L, perfilEditado));
    }

    @Test
    void deveTentarExcluirUmPerfilComID() {
        Perfil perfil = new Perfil(1L, "PROFESSOR");

        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));
        doNothing().when(perfilRepository).delete(perfil);

        assertDoesNotThrow(() -> perfilService.excluir(1L));
        verify(perfilRepository, times(1)).delete(perfil);
    }

    @Test
    void deveTentarExcluirUmPerfilComIdInvalidoeRetornarPerfilEntityNotFoundException() {
        when(perfilRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PerfilEntityNotFoundException.class, () -> perfilService.excluir(1L));
    }

    @Test
    void deveBuscarTodosOsObjetosDoPerfilExistentes() {
        List<Perfil> perfis = List.of(new Perfil(1L, "PROFESSOR"), new Perfil(2L, "ADMIN"));

        when(perfilRepository.findAll()).thenReturn(perfis);

        List<Perfil> encontrados = perfilService.buscarTodos();

        assertEquals(2, encontrados.size());
    }

    @Test
    void deveBuscarTodosOsObjetosDoPerfilInexistentes() {
        when(perfilRepository.findAll()).thenReturn(List.of());

        List<Perfil> encontrados = perfilService.buscarTodos();

        assertTrue(encontrados.isEmpty());
    }
}


