package com.example.ecommerce_website.Controller;

import com.example.ecommerce_website.ApiResponse.ApiResponse;
import com.example.ecommerce_website.Model.Category;
import com.example.ecommerce_website.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@Valid
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    //object from service
    private final CategoryService categoryService;


    @GetMapping("/get")
    public ResponseEntity<?>getCategories(){
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }
    @PostMapping("/add")
    public ResponseEntity<?>addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
       categoryService.addCategory(category);
        return ResponseEntity.status(200).body("add done");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteCategory(@PathVariable String id){
        boolean isDeleted=categoryService.deleteCategory(id);
        if(isDeleted){
            return  ResponseEntity.status(200).body(new ApiResponse("deleted done"));
        }else
            return ResponseEntity.status(400).body(new ApiResponse("id not found in this system"));

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateCategories(@PathVariable String id ,@RequestBody @Valid Category category , Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated=categoryService.updateCategories(id,category);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("updated done"));
        }else
            return ResponseEntity.status(400).body(new ApiResponse("can not updated this id not found"));
    }
    @GetMapping("/get-Categories-Name-By-Id/{categoryId}")
    public ResponseEntity<?>getCategoriesNameById(@PathVariable String categoryId) {
        String message = categoryService.getCategoriesNameById(categoryId);
        if (message.equalsIgnoreCase("id not found")) {
            return ResponseEntity.status(400).body(message);


        } else {
            return ResponseEntity.status(200).body(message);
        }

    }}