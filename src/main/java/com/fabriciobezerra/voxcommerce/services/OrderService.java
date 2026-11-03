package com.fabriciobezerra.voxcommerce.services;

import com.fabriciobezerra.voxcommerce.dto.OrderDto;
import com.fabriciobezerra.voxcommerce.dto.OrderItemDto;
import com.fabriciobezerra.voxcommerce.entities.*;
import com.fabriciobezerra.voxcommerce.repositories.OrderItemRepository;
import com.fabriciobezerra.voxcommerce.repositories.OrderRepository;
import com.fabriciobezerra.voxcommerce.repositories.ProductRepository;
import com.fabriciobezerra.voxcommerce.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final UserService userService;

    private final ProductRepository productRepository;

    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado! Id: " + id + "."));
        return new OrderDto(order);
    }

    @Transactional
    public OrderDto insert(OrderDto dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User client = userService.authenticated();
        order.setClient(client);

        for (OrderItemDto itemDto : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDto.getProductId());
            OrderItem orderItem = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
            order.getItems().add(orderItem);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDto(order);
    }
}
