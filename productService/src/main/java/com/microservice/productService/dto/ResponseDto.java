package com.microservice.productService.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto {

    private String productId;
    private String name;
    private String description;
    private String price;
}
