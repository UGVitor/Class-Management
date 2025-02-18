package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.repository.Projection.LoginProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    boolean existsByLogin(String nome);

    @Query("SELECT l.id AS id, l.login AS login, p.nome AS perfilNome " +
            "FROM Login l JOIN l.perfil p")
    List<LoginProjection> findAllLoginsWithPerfilNome();

    @Query("SELECT l.id AS id, l.login AS login, p.nome AS perfilNome " +
            "FROM Login l JOIN l.perfil p " +
            "WHERE p.nome = 'Professor'")
    List<LoginProjection> findAllLoginsByPerfilProfessor();

    Optional<Login> findByLogin(String login);


}
