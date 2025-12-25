package com.microservice.orderService.service;


import com.microservice.orderService.dto.OrderLineItemsDto;
import com.microservice.orderService.dto.OrderRequestDto;
import com.microservice.orderService.model.Order;
import com.microservice.orderService.model.OrderLineItems;
import com.microservice.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDto orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> items = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToEntity)
                .toList();

        order.setOrderLineItemsList(items);
        orderRepository.save(order);
    }

    private OrderLineItems mapToEntity(OrderLineItemsDto dto) {
        OrderLineItems item = new OrderLineItems();
        item.setSkuCode(dto.getSkuCode());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        return item;
    }

}
