package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.PeriodoDTO;
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
@Sql(scripts = "/sql/periodo/periodo-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/periodo/periodo-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class PeriodoControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarPeriodo_ComPeriodoValido_RetornarPeriodoCriadoComStatus201() {
        PeriodoDTO responseBody = testClient
                .post()
                .uri("/api/v1/periodos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PeriodoDTO(null, "Quinto"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PeriodoDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Quinto");
    }

    @Test
    public void deveCriarPeriodo_ComPeriodoInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/periodos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PeriodoDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarPeriodo_ComPeriodoRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/periodos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PeriodoDTO(100L, "Terceiro"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarPeriodo_ComIdExistente_RetornarPeriodoComStatus200() {
        PeriodoDTO responseBody = testClient
                .get()
                .uri("/api/v1/periodos/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PeriodoDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Segundo");
    }

    @Test
    public void deveBuscarPeriodo_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/periodos/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarPeriodo_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/periodos/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PeriodoDTO(100L, "Sexto"))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarPeriodo_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/periodos/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PeriodoDTO(0L, "Sexto"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarPeriodo_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/periodos/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PeriodoDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarPeriodo_ComIdExistente_RetornarComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/periodos/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarPeriodo_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/periodos/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarPeriodo_SemQualquerParametro_RetornarComStatus200() {
        testClient
                .get()
                .uri("/api/v1/periodos")
                .exchange()
                .expectStatus().isOk();
    }
}
