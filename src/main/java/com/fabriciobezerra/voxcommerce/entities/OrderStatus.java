package com.fabriciobezerra.voxcommerce.entities;

public enum OrderStatus {
    WAITING_PAYMENT("Aguardando Pagamento"),
    PAID("Pago"),
    SHIPPED("Enviado"),
    DELIVERED("Entregue"),
    CANCELED("Cancelado");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
