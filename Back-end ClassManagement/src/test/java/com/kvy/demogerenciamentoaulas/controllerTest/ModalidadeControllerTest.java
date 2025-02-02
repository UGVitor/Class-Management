package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.ModalidadeDTO;
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
@Sql(scripts = "/sql/modalidade/modalidade-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/modalidade/modalidade-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class ModalidadeControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarModalidade_ComModalidadeValido_RetornarModalidadeCriadoComStatus201() {
        ModalidadeDTO responseBody = testClient
                .post()
                .uri("/api/v1/modalidades")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ModalidadeDTO(null, "Subsequente"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ModalidadeDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Subsequente");
    }

    @Test
    public void deveCriarModalidade_ComModalidadeInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/modalidades")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ModalidadeDTO(null, ""))
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
                .uri("/api/v1/modalidades")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ModalidadeDTO(100L, "Integrado"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarModalidade_ComIdExistente_RetornarModalidadeComStatus200() {
        ModalidadeDTO responseBody = testClient
                .get()
                .uri("/api/v1/modalidades/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ModalidadeDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Integrado");
    }

    @Test
    public void deveBuscarModalidade_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/modalidades/0")
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
                .uri("/api/v1/modalidades/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ModalidadeDTO(100L, "SubSequentes"))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarModalidade_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/modalidades/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ModalidadeDTO(0L, "SubSequentes"))
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
                .uri("/api/v1/modalidades/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ModalidadeDTO(null, ""))
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
                .uri("/api/v1/modalidades/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarModalidade_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/modalidades/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarModalidade_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/modalidades")
                .exchange()
                .expectStatus().isOk();
    }
}
