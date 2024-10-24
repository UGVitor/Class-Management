package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.service.ModalidadeService;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Modalidades", description = "Contém todas as operações relativas aos recursos de CRUD de modalidade.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/modalidades")
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
    public ResponseEntity<Modalidade> createModalidade(@RequestBody Modalidade modalidade) {
        Modalidade savedModalidade = modalidadeService.salvar(modalidade);
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
    public ResponseEntity<Modalidade> updateModalidade(@PathVariable Long id, @RequestBody Modalidade modalidade) {
        Modalidade updatedModalidade = modalidadeService.editar(id, modalidade);
        return ResponseEntity.ok(updatedModalidade);
    }




}
