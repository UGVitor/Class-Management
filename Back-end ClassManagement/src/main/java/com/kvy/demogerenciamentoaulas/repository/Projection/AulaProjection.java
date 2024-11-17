package com.kvy.demogerenciamentoaulas.repository.Projection;

public interface AulaProjection {
    Long getId();
    String getDescricao();
    DisciplinaProjection getDisciplina();
    SalaProjection getSala();
    HorarioProjection getHorario();
    //DiasDaSemanaProjection getDiasDaSemana();

    interface DisciplinaProjection {
        Long getId();
        String getNome();
    }

    interface SalaProjection {
        Long getId();
        String getNome();
    }

    interface HorarioProjection {
        Long getId();
        String getNome();
    }

    interface DiasDaSemanaProjection {
        Long getId();
        String getNome();
    }
}
