package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class AulaResponseDto {

    private Long id;

    private String topico;

    private LocalDate data;



}
