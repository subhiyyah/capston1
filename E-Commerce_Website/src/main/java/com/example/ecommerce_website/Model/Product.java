package com.example.ecommerce_website.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
    @NotEmpty(message = "id can not be a null value")
    private String id;
    @NotEmpty(message = "name can not be null value")
    @Size(min = 4,message = "length of name must be greater than 3")
    private String name;
    @NotNull(message = "price can not be null or empty")
    @Positive(message = "price must be a positive number")
    private double price;
    @NotEmpty(message = "categorty Id can not be a null value")
    private String categortyId;
}