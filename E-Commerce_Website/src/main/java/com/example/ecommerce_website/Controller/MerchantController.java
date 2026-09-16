package com.example.ecommerce_website.Controller;

import com.example.ecommerce_website.ApiResponse.ApiResponse;
import com.example.ecommerce_website.Model.Merchant;
import com.example.ecommerce_website.Service.MerchantService;
import com.example.ecommerce_website.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@Valid
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class MerchantController {
    //object form service layer
    private final MerchantService merchantService;
    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity<?> getMerchant() {
        return ResponseEntity.status(200).body(merchantService.getMerchant());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body("add done");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("deleted done"));
        } else
            return ResponseEntity.status(400).body(new ApiResponse("id not found in this system"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("updated done"));
        } else
            return ResponseEntity.status(400).body(new ApiResponse("can not updated this id not found"));
    }

    @PutMapping("/addstock/{productId}/{merchantId}/{amountStock}")
    public ResponseEntity<?> addStock(@PathVariable String productId, @PathVariable String merchantId, @PathVariable int amountStock) {

        String message = merchantService.addStock(productId, merchantId, amountStock);
        if (message.contains("product")) {
            return ResponseEntity.status(400).body(new ApiResponse("product id not found"));
        }
        if (message.contains("merchant")) {
            return ResponseEntity.status(400).body(new ApiResponse("merchant id not found"));
        }
        if (message.contains("total")) {
            return ResponseEntity.status(400).body(new ApiResponse("total stock must above of 10"));
        }
        if (message.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("not found"));
        } else


            return ResponseEntity.status(200).body("stock updated");
    }

    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {

        String message = merchantService.buyProduct(userId, productId, merchantId);
        if (message.equalsIgnoreCase("user id not found")) {
            return ResponseEntity.status(400).body(message);
        }

        if (message.equalsIgnoreCase("product id not found")) {
            return ResponseEntity.status(400).body(message);

        }

        if (message.equalsIgnoreCase("merchant id not found for this product")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("bad request / not in stock")) {
            return ResponseEntity.status(400).body(message);

        }
        if (message.equalsIgnoreCase("bad request / balance < price")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("buy done")) {
            return ResponseEntity.status(200).body(message);

        }


        return null;
    }

    @PutMapping("/dicount/{userId}/{productId}")
    public ResponseEntity<?> userDiscount(@PathVariable String userId, @PathVariable String productId) {

        String message = merchantService.userDiscount(userId, productId);
        if (message.equalsIgnoreCase("product id not found")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("user id not found")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("price updated , discount by 20% for student where id start with 11")) {
            return ResponseEntity.status(200).body(message);
        }
        return null;
    }

    @DeleteMapping("/delete-Merhant-By-Admin/{userId}/{merchantId}")
    public ResponseEntity<?> deleteMerhantByAdmin(@PathVariable String userId, @PathVariable String merchantId) {
        String message = merchantService.deleteMerhantByAdmin(userId, merchantId);
        if (message.equalsIgnoreCase("user have no permission")) {
            return ResponseEntity.status(400).body(message);

        }

        if (message.equalsIgnoreCase("user id not found")) {
            return ResponseEntity.status(400).body(message);

        }
        if (message.equalsIgnoreCase("merchants id not found")) {
            return ResponseEntity.status(400).body(message);
        }
        if (message.equalsIgnoreCase("remove merchants successfully")) {
            return ResponseEntity.status(200).body(new ApiResponse(message));

        }


        return ResponseEntity.status(200).body(">???????????????");
    }
}
//end buy method