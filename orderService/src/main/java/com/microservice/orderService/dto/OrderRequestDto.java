package com.microservice.orderService.dto;

import com.microservice.orderService.model.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private List<OrderLineItemsDto> orderLineItemsList;

}
