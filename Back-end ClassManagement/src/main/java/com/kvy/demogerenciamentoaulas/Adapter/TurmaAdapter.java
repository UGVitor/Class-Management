package com.kvy.demogerenciamentoaulas.Adapter;

import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;

public class TurmaAdapter {
        public static Turma toEntity(TurmaDTO dto) {
            return Turma.builder()
                    .id(dto.getId())
                    .nome(dto.getNome())
                    .build();
        }

        public static TurmaDTO toDTO(Turma turma) {
            return TurmaDTO.builder()
                    .id(turma.getId())
                    .nome(turma.getNome())
                    .build();
        }
    }


