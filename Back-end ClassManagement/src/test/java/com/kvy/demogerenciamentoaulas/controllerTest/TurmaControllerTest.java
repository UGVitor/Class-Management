package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;
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
@Sql(scripts = "/sql/turma/turma-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/turma/turma-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class TurmaControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarTurma_ComTurmaValido_RetornarTurmaCriadoComStatus201() {
        TurmaDTO responseBody = testClient
                .post()
                .uri("/api/v1/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TurmaDTO(null, "ADS4N", 4L, 3L, 1L, 2L))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TurmaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("ADS4N");
    }

    @Test
    public void deveCriarTurma_ComTurmaInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TurmaDTO(null, "", null, null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarTurma_ComTurmaRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TurmaDTO(100L, "ADS4M", 4L, 1L, 1L, 2L))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarTurma_ComIdExistente_RetornarTurmaComStatus200() {
        TurmaDTO responseBody = testClient
                .get()
                .uri("/api/v1/turmas/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TurmaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("ADS4M");
    }

    @Test
    public void deveBuscarTurma_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/turmas/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarTurma_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/turmas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TurmaDTO(100L, "ADS4M", 4L, 1L, 1L, 2L))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarTurma_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/turmas/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TurmaDTO(0L, "ADS4M", 4L, 1L, 1L, 2L))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarTurma_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/turmas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TurmaDTO(null, "", null, null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarTurma_ComIdExistente_RetornarComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/turmas/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarTurma_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/turmas/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarTurma_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/turmas")
                .exchange()
                .expectStatus().isOk();
    }
}
