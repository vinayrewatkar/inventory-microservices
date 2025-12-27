package com.microservice.orderService.controller;


import com.microservice.orderService.dto.OrderRequestDto;
import com.microservice.orderService.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBack")
    public String placeOrder(@RequestBody OrderRequestDto orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order is placed";
    }

    public String fallBack(OrderRequestDto orderRequestDto, RuntimeException runtimeException){
        return "Oops! one or moreb services failed";
    }
}
