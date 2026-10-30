package com.fabriciobezerra.voxcommerce.dto;

import com.fabriciobezerra.voxcommerce.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private Instant moment;

    public PaymentDto(Payment entity) {
        id = entity.getId();
        moment = entity.getMoment();
    }
}
