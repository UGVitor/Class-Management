package com.kvy.demogerenciamentoaulas.web.dto.ResponseDTO;

import com.kvy.demogerenciamentoaulas.entity.Curso;
import lombok.Data;

@Data
public class CursoResponseDTO {
    private Long id;
    private String curso;
    private String modalidadeNome;

    public CursoResponseDTO(Curso curso) {
        this.id = curso.getId();
        this.curso = curso.getCurso();
        this.modalidadeNome = curso.getModalidadeNome();
    }

    public CursoResponseDTO(Long id, String curso, String s) {
    }
}
