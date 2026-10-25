package com.fabriciobezerra.voxcommerce.dto;

import com.fabriciobezerra.voxcommerce.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductMinDto {
    private Long id;
    private String name;
    private Double price;
    private String imgUrl;

    public ProductMinDto(Long id, String name, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductMinDto(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }
}
