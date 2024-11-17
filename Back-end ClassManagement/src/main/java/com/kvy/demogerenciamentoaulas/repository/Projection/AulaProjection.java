package com.kvy.demogerenciamentoaulas.repository.Projection;

public interface AulaProjection {
    Long getId();
    String getDescricao();
    String getDisciplinaNome(); // O nome do método deve corresponder ao campo na entidade
    String getSalaNome();       // O nome do método deve corresponder ao campo na entidade
    String getHorarioNome();    // O nome do método deve corresponder ao campo na entidade
    String getDiaSemanaNome();  // O nome do método deve corresponder ao campo na entidade
}
