package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.DiaSemanaDTO;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/diasdaSemana/diasdaSemana-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/diasdaSemana/diasdaSemana-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class DiasDaSemanaControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarDiadaSemana_ComDiadaSemanaValido_RetornarDiadaSemanaCriadoComStatus201() {
        DiaSemanaDTO responseBody = testClient
                .post()
                .uri("/api/v1/diasSemana")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DiaSemanaDTO(null, "Nona"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(DiaSemanaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getDia()).isEqualTo("Nona");
    }

    @Test
    public void deveCriarDiadaSemana_ComDiadaSemanaInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/diasSemana")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DiaSemanaDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarDiadaSemana_ComDiadaSemanaRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/diasSemana")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DiaSemanaDTO(100L, "Setima"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarDiadaSemana_ComIdExistente_RetornarDiadaSemanaComStatus200() {
        DiaSemanaDTO responseBody = testClient
                .get()
                .uri("/api/v1/diasSemana/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(DiaSemanaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getDia()).isEqualTo("Setima");
    }

    @Test
    public void deveBuscarDiadaSemana_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/diasSemana/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarDiadaSemana_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/diasSemana/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DiaSemanaDTO(100L, "Setima"))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarDiadaSemana_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/diasSemana/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DiaSemanaDTO(0L, "Setima"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarDiadaSemana_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/diasSemana/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DiaSemanaDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarDiadaSemana_ComIdExistente_RetornarComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/diasSemana/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarDiadaSemana_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/diasSemana/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarDiadaSemana_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/diasSemana")
                .exchange()
                .expectStatus().isOk();
    }
}
