package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.exception.ModalidadeEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.service.ModalidadeService;
import com.kvy.demogerenciamentoaulas.web.dto.ModalidadeDTO;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;


@Tag(name = "Modalidades", description = "Contém todas as operações relativas aos recursos de CRUD de modalidade.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/modalidades")
//@CrossOrigin(origins = "*")

public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    @Operation(summary = "Criar uma nova modalidade", description = "Recurso para criar uma nova modalidade",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Modalidade.class))),
                    @ApiResponse(responseCode = "409", description = "Modalidade já cadastrada no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Modalidade> createModalidade(@Valid @RequestBody ModalidadeDTO modalidadeDTO) {

        Modalidade savedModalidade = modalidadeService.salvar(modalidadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedModalidade);
    }

    @Operation(summary = "Atualizar uma modalidade", description = "Atualizar uma modalidade pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Modalidade.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Modalidade> updateModalidade(@PathVariable Long id, @Valid @RequestBody ModalidadeDTO modalidadeDTO) {
        Modalidade updatedModalidade = modalidadeService.editar(id, modalidadeDTO);
        return ResponseEntity.ok(updatedModalidade);
    }

    @Operation(summary = "Recuperar Modalidade por ID", description = "Recurso para recuperar uma modalidade pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Modalidade recuperada com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Modalidade.class))),
                    @ApiResponse(responseCode = "404", description = "Modalidade não encontrada.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Modalidade> getById(@PathVariable Long id) {
        Modalidade modalidade = modalidadeService.buscarPorId(id);
        return ResponseEntity.ok(modalidade);
    }

    @Operation(summary = "Excluir modalidade por ID", description = "Recurso para excluir uma modalidade pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Modalidade excluída com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Modalidade não encontrada.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriodo(@PathVariable Long id) {
        modalidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos as modalidades", description = "Recurso para listar todas as modalidades cadastradas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Modalidades listadas com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Modalidade.class)))
            })
    @GetMapping
    public ResponseEntity<List<Modalidade>> getAllModalidades() {
        List<Modalidade> modalidades = modalidadeService.buscarTodos();
        return ResponseEntity.ok(modalidades);
    }

}
