package com.microservice.orderService.dto;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderLineItemsDto {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
