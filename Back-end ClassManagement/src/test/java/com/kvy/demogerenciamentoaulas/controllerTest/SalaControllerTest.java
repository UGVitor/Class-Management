package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.SalaDTO;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/sala/sala-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/sala/sala-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class SalaControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarSala_ComSalaValido_RetornarSalaCriadoComStatus201() {
        SalaDTO responseBody = testClient
                .post()
                .uri("/api/v1/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SalaDTO(null, 1L, 5, 36))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(SalaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumero()).isEqualTo(5);
    }

    @Test
    public void deveCriarSala_ComSalaInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SalaDTO(null,null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveBuscarSala_ComIdExistente_RetornarSalaComStatus200() {
        SalaDTO responseBody = testClient
                .get()
                .uri("/api/v1/salas/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(SalaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
    }

    @Test
    public void deveBuscarSala_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/salas/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarSala_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/salas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SalaDTO(100L, 1L, 35, 7))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarSala_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/salas/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SalaDTO(100L, 1L, 35, 7))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarSala_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/salas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SalaDTO(null, null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarSala_ComIdExistente_RetornarComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/salas/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarSala_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/salas/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarSala_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/salas")
                .exchange()
                .expectStatus().isOk();
    }
}
