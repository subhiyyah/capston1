package com.example.ecommerce_website.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @NotEmpty(message = "id can not be null")
    private String id;

    @NotEmpty(message = "user name can not be empty")
    @Size(min = 6,message = "length name have be more than 5")
    private String username;

    @NotEmpty(message = "password can not be empty")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Password must be at least 6 characters long and contain both letters and digits"
    )
    private String password;

    @NotEmpty(message = "email must be not empty")
    @Email(message = "must have a @ ")
    private String email;

    @NotEmpty(message = "role must be not empty")
    //pattern
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be either Admin or Customer")
    private String role;



    @NotNull(message = "balance must be not null or empty")
    @Positive(message = "balance must be a positive value")
    private double balance;


}