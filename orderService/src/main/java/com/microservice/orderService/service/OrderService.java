package com.microservice.orderService.service;


import com.microservice.orderService.config.WebClientConfig;
import com.microservice.orderService.dto.InventoryResponse;
import com.microservice.orderService.dto.OrderLineItemsDto;
import com.microservice.orderService.dto.OrderRequestDto;
import com.microservice.orderService.model.Order;
import com.microservice.orderService.model.OrderLineItems;
import com.microservice.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final WebClient webClient;

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDto orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> items = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToEntity)
                .toList();

        order.setOrderLineItemsList(items);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

         System.out.println("making request to inventory service");
         log.info("Making call to inventory");
         InventoryResponse[] inventoryResponses = webClient.get()
                 .uri("http://localhost:8083/api/inventory", uriBuilder ->
                         uriBuilder.queryParam("skuCode", skuCodes).build())
                 .retrieve()
                 .bodyToMono(InventoryResponse[].class)
                 .block();


        assert inventoryResponses != null;
        boolean allResponses = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(allResponses){
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product is not in inventory");
        }

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
