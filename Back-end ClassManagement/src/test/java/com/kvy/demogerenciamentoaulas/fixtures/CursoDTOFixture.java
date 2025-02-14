package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.CursoDTO;

public class CursoDTOFixture {

    public static CursoDTO fixtureCursoDTO1() {
        return CursoDTO.builder()
                .id(1L)
                .curso("Análise e Desenvolvimento de Sistemas")
                .modalidade(1L) // Adicionando a modalidade
                .build();
    }

    public static CursoDTO fixtureCursoDTO2() {
        return CursoDTO.builder()
                .id(2L)
                .curso("Redes de Computadores")
                .modalidade(1L) // Adicionando a modalidade
                .build();
    }

    public static CursoDTO fixtureCursoDTO3() {
        return CursoDTO.builder()
                .id(3L)
                .curso("Informática para Internet")
                .modalidade(1L) // Adicionando a modalidade
                .build();
    }

    public static CursoDTO fixtureCursoDTONullName() {
        return CursoDTO.builder()
                .id(5L)
                .curso(null)
                .modalidade(1L) // Adicionando a modalidade
                .build();
    }

    public static CursoDTO fixtureCursoDTOEmptyName() {
        return CursoDTO.builder()
                .id(6L)
                .curso("")
                .modalidade(1L) // Adicionando a modalidade
                .build();
    }

    public static CursoDTO fixtureCursoDTOIdInvalido() {
        return CursoDTO.builder()
                .id(null)
                .curso("")
                .modalidade(1L) // Adicionando a modalidade
                .build();
    }
}
