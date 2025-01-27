package com.kvy.demogerenciamentoaulas.Adapter;

import com.kvy.demogerenciamentoaulas.entity.DiaSemana;
import com.kvy.demogerenciamentoaulas.web.dto.DiaSemanaDTO;

public class DiaSemanaAdapter {

        public static DiaSemana toEntity(DiaSemanaDTO dto) {
            return DiaSemana.builder()
                    .id(dto.getId())
                    .dia(dto.getDia())
                    .build();
        }

        public static DiaSemanaDTO toDTO(DiaSemana diaSemana) {
            return DiaSemanaDTO.builder()
                    .id(diaSemana.getId())
                    .dia(diaSemana.getDia())
                    .build();
        }
    }

