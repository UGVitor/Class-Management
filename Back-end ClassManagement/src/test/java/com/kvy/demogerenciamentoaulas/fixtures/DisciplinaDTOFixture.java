package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaDTO;

public class DisciplinaDTOFixture {

    public static DisciplinaDTO fixtureDisciplinaDTO1() {
        return DisciplinaDTO.builder()
                .id(1L)
                .nome("Português")
                .loginId(1L)
                .build();
    }

    public static DisciplinaDTO fixtureDisciplinaDTO2() {
        return DisciplinaDTO.builder()
                .id(2L)
                .nome("Matematica")
                 .loginId(1L)
                .build();
    }

    public static DisciplinaDTO fixtureDisciplinaDTONullName() {
        return DisciplinaDTO.builder()
                .id(3L)
                .nome(null)
                 .loginId(1L)
                .build();
    }

    public static DisciplinaDTO fixtureDisciplinaDTONullLogin() {
        return DisciplinaDTO.builder()
                .id(3L)
                .nome("História")
                .loginId(null)
                .build();
    }

    public static DisciplinaDTO fixtureDisciplinaDTOEmptyName() {
        return DisciplinaDTO.builder()
                .id(4L)
                .nome("")
                 .loginId(1L)
                .build();
    }
    public static DisciplinaDTO fixtureDisciplinaDTOIdInvalido() {
        return DisciplinaDTO.builder()
                .id(null)
                .nome("Inglês")
                 .loginId(1L)
                .build();
    }
}
