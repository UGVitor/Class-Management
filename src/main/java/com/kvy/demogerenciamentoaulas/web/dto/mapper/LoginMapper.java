package com.kvy.demogerenciamentoaulas.web.dto.mapper;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.web.dto.LoginCreateDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class LoginMapper {

    public static LoginResponseDto toDto(Login login) {
        String role = login.getRole().name().substring("ROLE_".length());

        PropertyMap<Login, LoginResponseDto> props = new PropertyMap<Login, LoginResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(login, LoginResponseDto.class);
    }

    public static Login toLogin(LoginCreateDTO createDTO) {
        return new ModelMapper().map(createDTO, Login.class);
    }

    public static List<LoginResponseDto> toListDto(List<Login> logins) {
        return logins.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
