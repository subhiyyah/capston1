package com.example.ecommerce_website.Controller;

import com.example.ecommerce_website.ApiResponse.ApiResponse;
import com.example.ecommerce_website.Model.Product;
import com.example.ecommerce_website.Model.User;
import com.example.ecommerce_website.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Valid
public class UserController {
    //object from user service
    private final UserService userService;
//    private final ProductService productService;


    @GetMapping("/get")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(200).body(userService.getUser());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body("add done");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("updated done"));
        } else
            return ResponseEntity.status(400).body(new ApiResponse("can not updated this id not found"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("deleted done"));
        } else
            return ResponseEntity.status(400).body(new ApiResponse("id not found in this system"));

    }

    @PutMapping("/UpdateBalance/{id}/{amount}")
    public ResponseEntity<?> UpdateBalance(@PathVariable String id, @PathVariable double amount) {
        String message = userService.UpdateBalance(id, amount);
        if (message.equalsIgnoreCase("the total balanc can not be negative")) {
            return ResponseEntity.status(400).body(message);

        }

        if (message.equalsIgnoreCase("id not found")) {
            return ResponseEntity.status(400).body(message);

        }
        if (message.equalsIgnoreCase("blance updated")) {
            return ResponseEntity.status(200).body(message);
        }
        return ResponseEntity.status(400).body(new ApiResponse("????????"));

    }

    @GetMapping("/Show-Product-By-Permissions/{userid}")
    public ResponseEntity<?> ShowProductByPermissions(@PathVariable String userid) {
        ArrayList<Product> products;
        products = userService.ShowProductByPermissions(userid);
        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("`id not found`"));
        } else
            return ResponseEntity.status(200).body(products);


    }

//    @DeleteMapping("/delete-Merhant-By-Admin/{userId}/{merchantId}")
//    public ResponseEntity<?> deleteMerhantByAdmin(@PathVariable String userId, @PathVariable String merchantId) {
//        String message = userService.deleteMerhantByAdmin(userId, merchantId);
//        if (message.equalsIgnoreCase("user have no permission")) {
//            return ResponseEntity.status(400).body(message);
//
//        }
//
//        if (message.equalsIgnoreCase("user id not found")) {
//            return ResponseEntity.status(400).body(message);
//
//        }
//        if (message.equalsIgnoreCase("merchant id not found")) {
//            return ResponseEntity.status(200).body(message);
//        }
//        if (message.equalsIgnoreCase("remove merchants successfully")) {
//            return ResponseEntity.status(200).body(new ApiResponse(message));
//
//        }
//
//
//        return ResponseEntity.status(200).body(">???????????????");
//    }
}