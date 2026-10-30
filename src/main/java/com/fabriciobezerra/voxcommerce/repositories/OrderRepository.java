package com.fabriciobezerra.voxcommerce.repositories;

import com.fabriciobezerra.voxcommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
