package com.example.ecommerce_website.Controller;

import com.example.ecommerce_website.ApiResponse.ApiResponse;
import com.example.ecommerce_website.Model.MerchantStock;
import com.example.ecommerce_website.Model.Product;
import com.example.ecommerce_website.Service.MerchantService;
import com.example.ecommerce_website.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@Valid
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchantstoke")
public class MerchantStockController {
    //object from service layer
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStroks(){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStroks());
    }

    @PostMapping("/add")
    public ResponseEntity<?>addMerchantStocks(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        merchantStockService.addMerchantStocks(merchantStock);
        return ResponseEntity.status(200).body("add done");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateMerchantStock(@PathVariable String id ,@RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated=merchantStockService.updateMerchantStock(id,merchantStock);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("updated done"));
        }else
            return ResponseEntity.status(400).body(new ApiResponse("can not updated this id not found"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteMerchantStock(@PathVariable String id){
        boolean isDeleted=merchantStockService.deleteMerchantStock(id);
        if(isDeleted){
            return  ResponseEntity.status(200).body(new ApiResponse("deleted done"));
        }else
            return ResponseEntity.status(400).body(new ApiResponse("id not found in this system"));

    }



}