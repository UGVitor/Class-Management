package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaDTO {

    private Long id;
    private String sala;
    private Long tipoSala;

    public Long getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(Long tipoSala) {
        this.tipoSala = tipoSala;}

}
