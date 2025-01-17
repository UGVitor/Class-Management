package com.kvy.demogerenciamentoaulas.jwt;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginService.buscarPorUsername(username);
        return new JwtUserDetails(login);
    }

    public JwtToken getTokenAuthenticated(String username) {
        String role = loginService.buscarPerfilPorUsername(username);
        return JwtUtils.createToken(username, role);
    }
}
