package com.example.ecommerce_website.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "id can not be null value")
    private String id;
    @NotEmpty(message = "product id can not be null value")
    private String productId;
    @NotEmpty(message = "mehant id can not be null value")
    private String mechantId;
    @NotNull(message = "stock can not be null value")
    @Min(value = 10 , message = "min value of stock is 10 to can be start")
    private int stock;


}