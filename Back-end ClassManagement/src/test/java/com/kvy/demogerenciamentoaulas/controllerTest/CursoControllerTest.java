package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.CursoDTO;
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
@Sql(scripts = "/sql/curso/curso-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/curso/curso-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class CursoControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarCurso_ComCursoValido_RetornarCursoCriadoComStatus201() {
        CursoDTO responseBody = testClient
                .post()
                .uri("/api/v1/curso")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CursoDTO(null, "Medicina", 105L))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CursoDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCurso()).isEqualTo("Medicina");
    }

    @Test
    public void deveCriarCurso_ComCursoInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/curso")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CursoDTO(null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarCurso_ComCursoRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/curso")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CursoDTO(100L, "Engenharia", 105L))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarCurso_ComIdExistente_RetornarCursoComStatus200() {
        CursoDTO responseBody = testClient
                .get()
                .uri("/api/v1/curso/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CursoDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
    }

    @Test
    public void deveBuscarCurso_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/curso/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarCurso_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/curso/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CursoDTO(100L, "Engenharia", 105L))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarCurso_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/curso/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CursoDTO(100L, "Engenharia", 105L))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarCurso_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/curso/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CursoDTO(null, null, null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarCurso_ComIdExistente_RetornarComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/curso/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarCurso_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/curso/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarCurso_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/curso")
                .exchange()
                .expectStatus().isOk();
    }
}
