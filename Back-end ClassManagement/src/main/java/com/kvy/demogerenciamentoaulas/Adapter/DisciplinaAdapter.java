package com.kvy.demogerenciamentoaulas.Adapter;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaDTO;

public class DisciplinaAdapter {


    public static Disciplina toEntity(DisciplinaDTO dto, Login login) {
        if (dto == null || login == null) {
            throw new IllegalArgumentException("DTO e Login não podem ser nulos.");
        }

        return Disciplina.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .login(login)
                .build();
    }


    public static DisciplinaDTO toDTO(Disciplina disciplina) {
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não pode ser nula.");
        }

        return DisciplinaDTO.builder()
                .id(disciplina.getId())
                .nome(disciplina.getNome())
                .loginId(disciplina.getLogin() != null ? disciplina.getLogin().getId() : null)
                .build();
    }
}
