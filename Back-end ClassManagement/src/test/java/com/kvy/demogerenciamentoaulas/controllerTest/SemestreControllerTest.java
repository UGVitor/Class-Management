package com.kvy.demogerenciamentoaulas.controllerTest;

import com.kvy.demogerenciamentoaulas.web.dto.SemestreDTO;
import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;
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
@Sql(scripts = "/sql/semestre/semestre-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/semestre/semestre-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class SemestreControllerTest {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarSemestre_ComSemestreValido_RetornarSemestreCriadoComStatus201() {
        SemestreDTO responseBody = testClient
                .post()
                .uri("/api/v1/semestres")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SemestreDTO(null, "Quinto"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(SemestreDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getSemestre()).isEqualTo("Quinto");
    }

    @Test
    public void deveCriarSemestre_ComSemestreInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/semestres")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SemestreDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarSemestre_ComSemestreRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/semestres")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SemestreDTO(100L, "Terceiro"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarSemestre_ComIdExistente_RetornarSemestreComStatus200() {
        SemestreDTO responseBody = testClient
                .get()
                .uri("/api/v1/semestres/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(SemestreDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSemestre()).isEqualTo("Terceiro");
    }

    @Test
    public void deveBuscarSemestre_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/semestres/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarSemestre_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/semestres/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SemestreDTO(100L, "Sexto"))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarSemestre_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/semestres/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SemestreDTO(0L, "Sexto"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarSemestre_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/semestres/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SemestreDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarSemestre_ComIdExistente_RetornarUsuarioComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/semestres/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarSemestre_ComIdInexistente_RetornarComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/semestres/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarSemestre_SemQualquerParametro_RetornarListaDeSemestreComStatus200() {
        testClient
                .get()
                .uri("/api/v1/semestres")
                .exchange()
                .expectStatus().isOk();
    }
}
