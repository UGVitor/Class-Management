package com.kvy.demogerenciamentoaulas.repository.Projection;

import java.time.LocalTime;

public interface AulaProjection {
    Long getId();
    String getDescricao();
    String getDisciplinaNome(); // O nome do método deve corresponder ao campo na entidade
    String getSalaNome();
    int getSalaNumero();// O nome do método deve corresponder ao campo na entidade
    LocalTime getHorarioInicio();
    LocalTime getHorarioTermino();// O nome do método deve corresponder ao campo na entidade
    String getDiaSemanaNome();  // O nome do método deve corresponder ao campo na entidade
}
