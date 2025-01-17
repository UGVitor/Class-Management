package com.kvy.demogerenciamentoaulas.jwt;

import com.kvy.demogerenciamentoaulas.entity.Login;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private Login login;

    public JwtUserDetails(Login login) {
        super(login.getLogin(), login.getPassword(), AuthorityUtils.createAuthorityList(login.getPerfil().getNome()));
        this.login = login;
    }

    public Long getId(){
        return this.login.getId();
    }

    public String getRole(){
        return this.login.getPerfil().getNome();
    }
}
