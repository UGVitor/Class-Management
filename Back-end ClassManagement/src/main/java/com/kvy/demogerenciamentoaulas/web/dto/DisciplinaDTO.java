package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Long login;
    private Long turma;

}
