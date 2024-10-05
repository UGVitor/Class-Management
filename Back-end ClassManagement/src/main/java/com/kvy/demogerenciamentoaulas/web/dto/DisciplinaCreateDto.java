package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class DisciplinaCreateDto {

    @NotBlank(message = "Nome invalido")
    private String nome;
    @NotBlank(message = "Não pode ficar em branco")
    private String descricao;
    @NotBlank(message = "A informação é invalida")
    private int cargaHoraria;
    @NotBlank(message = "Codigo invalida")
    private Long cod_profesor;
}
