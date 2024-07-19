package com.kvy.demogerenciamentoaulas.jwt;

import com.kvy.demogerenciamentoaulas.entity.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtUserDetails extends User {

    private Login login;

    public JwtUserDetails(Login login) {
        super(login.getLogin(), login.getPassword(), AuthorityUtils.createAuthorityList(login.getRole().name()));
        this.login = login;
    }

    public Long getId(){
        return this.login.getId();
    }

    public String getRole(){
        return this.login.getRole().name();
    }
}
