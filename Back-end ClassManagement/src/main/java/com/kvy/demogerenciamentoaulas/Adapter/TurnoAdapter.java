package com.kvy.demogerenciamentoaulas.Adapter;

import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.web.dto.TurnoDTO;

public class TurnoAdapter {

    public static Turno toEntity(TurnoDTO dto) {
        return Turno.builder()
                .id(dto.getId())
                .turno(dto.getTurno())
                .build();
    }

    public static TurnoDTO toDTO(Turno turno) {
        return TurnoDTO.builder()
                .id(turno.getId())
                .turno(turno.getTurno())
                .build();
    }
}
