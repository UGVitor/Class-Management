package com.kvy.demogerenciamentoaulas.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.exception.TurnoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.service.TurnoService;
import com.kvy.demogerenciamentoaulas.web.controller.TurnoController;
import com.kvy.demogerenciamentoaulas.web.dto.TurnoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TurnoController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class TurnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TurnoService turnoService;

    @Test
    void deveCriarUmTurno() throws Exception {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setTurno("Matutino");

        Turno turno = new Turno();
        turno.setId(1L);
        turno.setTurno("Matutino");

        when(turnoService.salvar(any(TurnoDTO.class))).thenReturn(turno);

        mockMvc.perform(post("/api/v1/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(turnoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.turno").value("Matutino"));

        verify(turnoService, times(1)).salvar(any(TurnoDTO.class));
    }

    @Test
    void deveRetornarErroAoCriarTurnoJaExistente() throws Exception {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setTurno("Matutino");

        when(turnoService.salvar(any(TurnoDTO.class))).thenThrow(new IllegalStateException("Turno já cadastrado"));

        mockMvc.perform(post("/api/v1/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(turnoDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Turno já cadastrado"));

        verify(turnoService, times(1)).salvar(any(TurnoDTO.class));
    }


    @Test
    void deveRetornarUmTurnoPorId() throws Exception {
        Turno turno = new Turno();
        turno.setId(1L);
        turno.setTurno("Matutino");

        when(turnoService.buscarPorId(1L)).thenReturn(turno);

        mockMvc.perform(get("/api/v1/turnos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.turno").value("Matutino"));

        verify(turnoService, times(1)).buscarPorId(1L);
    }


    @Test
    void deveRetornarErroAoBuscarTurnoInexistente() throws Exception {
        when(turnoService.buscarPorId(1L)).thenThrow(new IllegalArgumentException("Turno não encontrado"));

        mockMvc.perform(get("/api/v1/turnos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Turno não encontrado"));

        verify(turnoService, times(1)).buscarPorId(1L);
    }


    @Test
    void deveRetornarTodosOsTurnos() throws Exception {
        Turno turno = new Turno();
        turno.setId(1L);
        turno.setTurno("Matutino");

        when(turnoService.buscarTodos()).thenReturn(List.of(turno));

        mockMvc.perform(get("/api/v1/turnos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].turno").value("Matutino"));

        verify(turnoService, times(1)).buscarTodos();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistemTurnos() throws Exception {
        when(turnoService.buscarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/turnos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(turnoService, times(1)).buscarTodos();
    }


    @Test
    void deveExcluirUmTurno() throws Exception {
        doNothing().when(turnoService).excluir(1L);

        mockMvc.perform(delete("/api/v1/turnos/1"))
                .andExpect(status().isNoContent());

        verify(turnoService, times(1)).excluir(1L);
    }

    @Test
    void deveRetornarErroAoExcluirTurnoInexistente() throws Exception {
        doThrow(new TurnoEntityNotFoundException("Turno não encontrado com o ID 1"))
                .when(turnoService).excluir(1L);

        mockMvc.perform(delete("/api/v1/turnos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Turno não encontrado com o ID 1"));

        verify(turnoService, times(1)).excluir(1L);
    }


}


