package com.example.ecommerce_website.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {
    @NotEmpty(message = "id can not be null")
    private String id;
    @NotEmpty(message = "name can not be null")
    @Size(min = 4 , message = "the length of name must be greater than 3")
    private String name;

}