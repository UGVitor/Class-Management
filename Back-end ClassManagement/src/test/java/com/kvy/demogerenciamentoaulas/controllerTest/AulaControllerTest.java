package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;
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
@Sql(scripts = "/sql/aula/aula-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/aula/aula-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class AulaControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarAula_ComAulaValido_RetornarAulaCriadoComStatus201() {
        AulaDTO responseBody = testClient
                .post()
                .uri("/api/v1/aulas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AulaDTO(null, 106L, 105L, 105L, 2L, 105L))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AulaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getDisciplinaId()).isNotNull();
    }

    @Test
    public void deveCriarAula_ComAulaInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/aulas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AulaDTO(null, null, null, null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarAula_ComAulaRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/aulas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AulaDTO(100L, 105L, 105L, 105L, 1L, 105L))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarAula_ComIdExistente_RetornarAulaComStatus200() {
        AulaDTO responseBody = testClient
                .get()
                .uri("/api/v1/aulas/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AulaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
    }

    @Test
    public void deveBuscarAula_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/aulas/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarAula_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/aulas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AulaDTO(100L, 105L, 105L, 105L, 2L, 105L))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarAula_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/aulas/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AulaDTO(100L, 105L, 105L, 105L, 2L, 105L))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarAula_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/aulas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AulaDTO(null, null, null, null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarAula_ComIdExistente_RetornarComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/aulas/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarAula_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/aulas/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarAula_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/aulas")
                .exchange()
                .expectStatus().isOk();
    }
}
