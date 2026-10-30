package com.fabriciobezerra.voxcommerce.dto;

import com.fabriciobezerra.voxcommerce.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String name;

    public ClientDto(User entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
