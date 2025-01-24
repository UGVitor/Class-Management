package com.kvy.demogerenciamentoaulas.Adapter;

import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;

public class TipoSalaAdapter {

    public static TipoSala toEntity(TipoSalaDTO dto) {
        return TipoSala.builder()
                .id(dto.getId())
                .tipoSala(dto.getTipoSala())
                .build();
    }

    public static TipoSalaDTO toDTO(TipoSala tipoSala) {
        return TipoSalaDTO.builder()
                .id(tipoSala.getId())
                .tipoSala(tipoSala.getTipoSala())
                .build();
    }
}
