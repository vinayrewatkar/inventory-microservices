package com.microservice.productService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RequestDto {
    private String name;
    private String description;
    private String price;
}
