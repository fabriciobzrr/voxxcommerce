package com.fabriciobezerra.voxcommerce.dto;

import com.fabriciobezerra.voxcommerce.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;

    private List<String> roles = new ArrayList<>();

    public UserDto(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
        birthDate = entity.getBirthDate();
        for (GrantedAuthority role : entity.getRoles()) {
            roles.add(role.getAuthority());
        }
    }
}
