package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter @Setter
public class Filtro {
        private LocalTime hora;
        private int duracao;
        private String disciplina;
        private String professor;
        private String periodo;

        public Filtro(LocalTime hora, int duracao, String disciplina, String professor, String periodo) {
            this.hora = hora;
            this.duracao = duracao;
            this.disciplina = disciplina;
            this.professor = professor;
            this.periodo = periodo;
        }

}



