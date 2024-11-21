package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplinaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Long loginId;
    private Long turmaId;

}
