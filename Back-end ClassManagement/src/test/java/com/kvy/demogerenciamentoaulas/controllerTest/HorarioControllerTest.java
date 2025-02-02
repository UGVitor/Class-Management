package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.HorarioDTO;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/horario/horario-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/horario/horario-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class HorarioControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarHorario_ComHorarioValido_RetornarHorarioCriadoComStatus201() {
        HorarioDTO responseBody = testClient
                .post()
                .uri("/api/v1/horarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new HorarioDTO(
                        null,
                        LocalTime.of(16, 0),
                        LocalTime.of(16, 50)
                ))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(HorarioDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getHoraInicio()).isEqualTo(LocalTime.of(16, 0));
        org.assertj.core.api.Assertions.assertThat(responseBody.getHoraTermino()).isEqualTo(LocalTime.of(16, 50));
    }

    @Test
    public void deveCriarHorario_ComHorarioInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/horarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new HorarioDTO(null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarModalidade_ComModalidadeRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/horarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new HorarioDTO(
                        null,
                        LocalTime.of(13, 15),
                        LocalTime.of(13, 50)
                ))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarModalidade_ComIdExistente_RetornarModalidadeComStatus200() {
        HorarioDTO responseBody = testClient
                .get()
                .uri("/api/v1/horarios/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(HorarioDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getHoraInicio()).isEqualTo(LocalTime.of(13, 15));
        org.assertj.core.api.Assertions.assertThat(responseBody.getHoraTermino()).isEqualTo(LocalTime.of(13, 50));
    }

    @Test
    public void deveBuscarModalidade_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/horarios/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarModalidade_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/horarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new HorarioDTO(
                        null,
                        LocalTime.of(13, 15),
                        LocalTime.of(13, 50)
                ))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarModalidade_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/horarios/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new HorarioDTO(
                        0L,
                        LocalTime.of(13, 15),
                        LocalTime.of(13, 50)
                ))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarModalidade_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/horarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new HorarioDTO(null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarModalidade_ComIdExistente_RetornarComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/horarios/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarModalidade_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/horarios/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarModalidade_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/horarios")
                .exchange()
                .expectStatus().isOk();
    }
}
