package com.kvy.demogerenciamentoaulas.Adapter;

import com.kvy.demogerenciamentoaulas.entity.Modalidade;

import com.kvy.demogerenciamentoaulas.web.dto.ModalidadeDTO;

public class ModalidadeAdapter {
    public static Modalidade toEntity(ModalidadeDTO dto) {
        return Modalidade.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .build();
    }

    public static ModalidadeDTO toDTO(Modalidade modalidade) {
        return ModalidadeDTO.builder()
                .id(modalidade.getId())
                .nome(modalidade.getNome())
                .build();
    }
}
