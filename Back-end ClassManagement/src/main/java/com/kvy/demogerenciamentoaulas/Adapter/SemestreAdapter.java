package com.kvy.demogerenciamentoaulas.Adapter;


import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.web.dto.SemestreDTO;


public class SemestreAdapter {
    public static Semestre toEntity(SemestreDTO dto) {
        return Semestre.builder()
                .id(dto.getId())
                .semestre(dto.getSemestre())
                .build();
    }

    public static SemestreDTO toDTO(Semestre semestre) {
        return SemestreDTO.builder()
                .id(semestre.getId())
                .semestre(semestre.getSemestre())
                .build();
    }
}
