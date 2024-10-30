package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CursoDTO {

    private Long id;
    private String curso;
    private Long modalidade;



    public Long getModalidade() {
        return modalidade;
    }

    public void setModalidade(Long modalidade) {
        this.modalidade = modalidade;
    }
}


