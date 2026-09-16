package com.example.ecommerce_website.Controller;

import com.example.ecommerce_website.ApiResponse.ApiResponse;
import com.example.ecommerce_website.Model.Product;
import com.example.ecommerce_website.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
@Valid
public class ProductController {//object from product Service
    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProduct() {
        return ResponseEntity.status(200).body(productService.getProduct());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProducts(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        productService.addProducts(product);
        return ResponseEntity.status(200).body("add done");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = productService.updateProduct(id, product);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("updated done"));
        } else
            return ResponseEntity.status(400).body(new ApiResponse("can not updated this id not found"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProducts(@PathVariable String id) {
        boolean isDeleted = productService.deleteProducts(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("deleted done"));
        } else
            return ResponseEntity.status(400).body(new ApiResponse("id not found in this system"));

    }


    @PutMapping("/update-Price-By-Category-Name/{productId}/{categoryId}")
    public ResponseEntity<?> updatePriceByCategoryName(@PathVariable String productId, @PathVariable String categoryId) {
        String message = productService.updatePriceByCategoryName(productId, categoryId);

        if (message.equalsIgnoreCase("category id not found")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("product id not found")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("have 20% discount by electric item")) {
            return ResponseEntity.status(200).body(message);
        }
        if (message.equalsIgnoreCase("have 30% discount by food item")) {
            return ResponseEntity.status(200).body(message);
        }
        if (message.equalsIgnoreCase("have 10% discount by clothing")) {
            return ResponseEntity.status(200).body(message);
        }
        return null;
    }

    @PutMapping("/set-Product-Price-Range-By-category-Name/{categoryid}/{productId}/{min}/{max}/{price}")
    public ResponseEntity<?> setProductPriceRangeBycategoryName(@PathVariable String categoryid, @PathVariable String productId, @PathVariable int min, @PathVariable int max, @PathVariable int price) {
        String message = productService.setProductPriceRangeBycategoryName(categoryid, productId, min, max, price);

        if (message.equalsIgnoreCase("min can not be greater than or equal max")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("min and max can not be zero")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("productId not found")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("category id not found")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("Electronics | set price successfully")) {
            return ResponseEntity.status(200).body(message);
        }
        if (message.equalsIgnoreCase("Food | set price successfully")) {
            return ResponseEntity.status(200).body(message);
        }
        if (message.equalsIgnoreCase("Clothing | set price successfully")) {
            return ResponseEntity.status(200).body(message);
        }

        return null;


    }
}