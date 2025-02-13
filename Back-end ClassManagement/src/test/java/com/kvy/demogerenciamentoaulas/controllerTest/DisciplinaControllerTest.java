package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaDTO;
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
@Sql(scripts = "/sql/disciplina/disciplina-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/disciplina/disciplina-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class DisciplinaControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarDisciplina_ComDisciplinaValido_RetornarDisciplinaCriadoComStatus201() {
        DisciplinaDTO responseBody = testClient
                .post()
                .uri("/api/v1/disciplinas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplinaDTO(null, "Sistemas Web", 105L))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(DisciplinaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Sistemas Web");
    }

    @Test
    public void deveCriarDisciplina_ComDisciplinaInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/disciplinas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplinaDTO(null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarDisciplina_ComDisciplinaRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/disciplinas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplinaDTO(100L, "Sistema", 105L))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarDisciplina_ComIdExistente_RetornarDisciplinaComStatus200() {
        DisciplinaDTO responseBody = testClient
                .get()
                .uri("/api/v1/disciplinas/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(DisciplinaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
    }

    @Test
    public void deveBuscarDisciplina_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/disciplinas/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarDisciplina_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/disciplinas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplinaDTO(100L, "Sistema", 105L))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarDisciplina_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/disciplinas/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplinaDTO(100L, "Sistema", 105L))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarDisciplina_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/disciplinas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplinaDTO(null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarDisciplina_ComIdExistente_RetornarComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/disciplinas/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarDisciplina_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/disciplinas/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarDisciplina_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/disciplinas")
                .exchange()
                .expectStatus().isOk();
    }
}
