package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByUsername(String username);

    @Query("select u.role from Login u where u.login like :login")
    Login.Role findRoleByUsername(String username);
}
