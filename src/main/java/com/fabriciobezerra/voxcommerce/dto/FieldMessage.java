package com.fabriciobezerra.voxcommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldMessage {
    private String fieldName;
    private String message;
}
