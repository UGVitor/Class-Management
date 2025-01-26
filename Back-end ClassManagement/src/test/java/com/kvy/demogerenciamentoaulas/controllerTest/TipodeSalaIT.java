package com.kvy.demogerenciamentoaulas.controllerTest;

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
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/tipodeSala/tipodeSala-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/tipodeSala/tipodeSala-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class TipodeSalaIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void deveCriarTipoSala_ComTipoDeSalaValido_RetornarTipoDeSalaCriadoComStatus201() {
        TipoSalaDTO responseBody = testClient
                .post()
                .uri("/api/v1/tiposalas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TipoSalaDTO(null, "Lab"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TipoSalaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTipoSala()).isEqualTo("Lab");
    }

    @Test
    public void deveCriarTipodeSala_ComTipodeSalaInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/tiposalas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TipoSalaDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveCriarTipodeSala_ComTipodeSalaRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/tiposalas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TipoSalaDTO(100L, "Escritorio"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void deveBuscarTipoSala_ComIdExistente_RetornarTipoSalaComStatus200() {
        TipoSalaDTO responseBody = testClient
                .get()
                .uri("/api/v1/tiposalas/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TipoSalaDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTipoSala()).isEqualTo("Escritorio");
    }

    @Test
    public void deveBuscarTipoSala_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/tiposalas/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarTipoSala_ComDadosValidos_RetornarStatus200() {
        testClient
                .put()
                .uri("/api/v1/tiposalas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TipoSalaDTO(100L, "Lab"))
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    public void deveEditarTipoSala_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/tiposalas/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TipoSalaDTO(0L, "Lab"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deveEditarTipoSala_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/tiposalas/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new TipoSalaDTO(null, ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void deveDeletarTipoSala_ComIdExistente_RetornarUsuarioComStatus204() {
        testClient
                .delete()
                .uri("/api/v1/tiposalas/100")
                .exchange()
                .expectStatus().isEqualTo(204);
    }

    @Test
    public void deveDeletarTipoSala_ComIdInexistente_RetornarUsuarioComStatus404() {
        testClient
                .delete()
                .uri("/api/v1/tiposalas/0")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void deveListarTipoSala_SemQualquerParametro_RetornarListaDeTiposComStatus200() {
        testClient
                .get()
                .uri("/api/v1/tiposalas")
                .exchange()
                .expectStatus().isOk();
    }
}
