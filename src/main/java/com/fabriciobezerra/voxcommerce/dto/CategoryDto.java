package com.fabriciobezerra.voxcommerce.dto;

import com.fabriciobezerra.voxcommerce.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;

    public CategoryDto(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
