package com.kvy.demogerenciamentoaulas.Adapter;

import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.web.dto.PeriodoDTO;

public class PeriodoAdapter {

    public static Periodo toEntity(PeriodoDTO dto) {
        return Periodo.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .build();
    }

    public static PeriodoDTO toDTO(Periodo periodo) {
        return PeriodoDTO.builder()
                .id(periodo.getId())
                .nome(periodo.getNome())
                .build();
    }
}
