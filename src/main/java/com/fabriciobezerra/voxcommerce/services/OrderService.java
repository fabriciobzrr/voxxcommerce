package com.fabriciobezerra.voxcommerce.services;

import com.fabriciobezerra.voxcommerce.dto.OrderDto;
import com.fabriciobezerra.voxcommerce.entities.Order;
import com.fabriciobezerra.voxcommerce.repositories.OrderRepository;
import com.fabriciobezerra.voxcommerce.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado! Id: " + id + "."));
        return new OrderDto(order);
    }
}
