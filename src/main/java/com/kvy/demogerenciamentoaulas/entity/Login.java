package com.kvy.demogerenciamentoaulas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "login")
public class Login implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
<<<<<<< HEAD
    @Column(name = "login", nullable = false, unique = true, length = 100)
    private String login;
=======
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
<<<<<<< HEAD
    private Role role = Role.ROLE_PROFESSOR;
=======
    private Role role;
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98

    public enum Role {
        ROLE_ADMIN, ROLE_PROFESSOR
    }

    @OneToMany(mappedBy = "professor")
    private Set<Disciplina> disciplinas = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(id, login.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                '}';
    }
}
