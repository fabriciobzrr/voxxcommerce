package com.fabriciobezerra.voxcommerce.dto;

import com.fabriciobezerra.voxcommerce.entities.Order;
import com.fabriciobezerra.voxcommerce.entities.OrderItem;
import com.fabriciobezerra.voxcommerce.entities.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Instant moment;
    private OrderStatus status;

    private ClientDto client;

    private PaymentDto payment;

    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    private List<OrderItemDto> items = new ArrayList<>();

    public OrderDto(Long id, Instant moment, OrderStatus status, ClientDto client, PaymentDto payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public OrderDto(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus();
        client = new ClientDto(entity.getClient());
        payment = (entity.getPayment() == null) ? null : new PaymentDto(entity.getPayment());

        for (OrderItem item : entity.getItems()) {
            items.add(new OrderItemDto(item));
        }
    }

    public Double getTotal() {
        Double total = 0.0;
        for (OrderItemDto orderItemDto : items) {
            total += orderItemDto.getSubTotal();
        }
        return total;
    }
}
