package com.kvy.demogerenciamentoaulas.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.PerfilEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.service.PerfilService;
import com.kvy.demogerenciamentoaulas.web.controller.PerfilController;
import com.kvy.demogerenciamentoaulas.web.dto.PerfilDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PerfilController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class PerfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PerfilService perfilService;

    @Test
    void deveCriarUmPerfil() throws Exception {
        PerfilDTO perfilDTO = new PerfilDTO(1L, "ADMIN");
        Perfil perfil = new Perfil(1L, "ADMIN");

        when(perfilService.salvar(any(PerfilDTO.class))).thenReturn(perfil);
        when(perfilService.convertToDTO(perfil)).thenReturn(perfilDTO);

        mockMvc.perform(post("/api/v1/perfis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(perfilDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("ADMIN"));

        // Verifica se o método do serviço foi chamado corretamente
        verify(perfilService, times(1)).salvar(any(PerfilDTO.class));
        verify(perfilService, times(1)).convertToDTO(perfil);
    }

    @Test
    void deveRetornarErroAoCriarPerfilJaExistente() throws Exception {
        PerfilDTO perfilDTO = new PerfilDTO(1L, "ADMIN");


        when(perfilService.salvar(any(PerfilDTO.class))).thenThrow(new IllegalStateException("Perfil já cadastrado"));

        mockMvc.perform(post("/api/v1/perfis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(perfilDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Perfil já cadastrado"));

        verify(perfilService, times(1)).salvar(any(PerfilDTO.class));
    }

    @Test
    void deveRetornarUmPerfilPorId() throws Exception {

        Long dummyId = 1L;
        Perfil perfil = new Perfil(dummyId, "ADMIN");
        PerfilDTO perfilDTO = new PerfilDTO(dummyId, "ADMIN");


        when(perfilService.buscarPorId(dummyId)).thenReturn(perfil);
        when(perfilService.convertToDTO(perfil)).thenReturn(perfilDTO);

        mockMvc.perform(get("/api/v1/perfis/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dummyId))
                .andExpect(jsonPath("$.nome").value("ADMIN"));

        verify(perfilService, times(1)).buscarPorId(dummyId);
    }


    @Test
    void deveRetornarErroAoBuscarPerfilInexistente() throws Exception {
        when(perfilService.buscarPorId(1L)).thenThrow(new IllegalArgumentException("Perfil não encontrado"));

        mockMvc.perform(get("/api/v1/perfis/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Perfil não encontrado"));

        verify(perfilService, times(1)).buscarPorId(1L);
    }

    @Test
    void deveRetornarTodosOsPerfis() throws Exception {
        Perfil perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNome("ADMIN");

        when(perfilService.buscarTodos()).thenReturn(List.of(perfil));

        mockMvc.perform(get("/api/v1/perfis"))
                .andExpect(status().isOk());


        verify(perfilService, times(1)).buscarTodos();}

    @Test
    void deveRetornarListaVaziaQuandoNaoExistemPerfis() throws Exception {
        when(perfilService.buscarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/perfis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(perfilService, times(1)).buscarTodos();
    }

    @Test
    void deveExcluirUmPerfil() throws Exception {
        doNothing().when(perfilService).excluir(1L);

        mockMvc.perform(delete("/api/v1/perfis/1"))
                .andExpect(status().isNoContent());

        verify(perfilService, times(1)).excluir(1L);
    }

    @Test
    void deveRetornarErroAoExcluirPerfilInexistente() throws Exception {
        doThrow(new PerfilEntityNotFoundException("Perfil não encontrado com o ID 1"))
                .when(perfilService).excluir(1L);

        mockMvc.perform(delete("/api/v1/perfis/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Perfil não encontrado com o ID 1"));

        verify(perfilService, times(1)).excluir(1L);
    }
}
