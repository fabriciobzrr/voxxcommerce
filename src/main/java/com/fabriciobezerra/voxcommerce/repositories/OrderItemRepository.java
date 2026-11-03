package com.fabriciobezerra.voxcommerce.repositories;

import com.fabriciobezerra.voxcommerce.entities.OrderItem;
import com.fabriciobezerra.voxcommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
