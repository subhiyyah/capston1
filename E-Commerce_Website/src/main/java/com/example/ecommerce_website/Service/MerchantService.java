package com.example.ecommerce_website.Service;

import com.example.ecommerce_website.Model.Merchant;
import com.example.ecommerce_website.Model.MerchantStock;
import com.example.ecommerce_website.Model.Product;
import com.example.ecommerce_website.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {
    //all Java code
    //all Array list

    //add object from merchantStok
    private final MerchantStockService merchantStockService;
    private final UserService userService;
    private final ProductService productService;
    ArrayList<Merchant> merchants = new ArrayList<>();

    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);

    }

    public boolean deleteMerchant(String id) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Merchant> getMerchant() {
        return merchants;
    }


    public boolean updateMerchant(String id, Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.set(i, merchant);
                return true;
            }

        }
        return false;
    }

    public String addStock(String productId, String merchantId, int amountStock) {
        ArrayList<MerchantStock> addStock = merchantStockService.getMerchantStroks();

        boolean productExists = false;
        boolean merchantExists = false;

        for (int i = 0; i < addStock.size(); i++) {
            boolean sameProduct = addStock.get(i).getProductId().equalsIgnoreCase(productId);
            boolean sameMerchant = addStock.get(i).getMechantId().equalsIgnoreCase(merchantId);

            if (sameProduct) productExists = true;
            if (sameMerchant) merchantExists = true;

            // العنصر المطابق للاثنين معًا
            if (sameProduct && sameMerchant) {
                if (addStock.get(i).getStock() + amountStock < 10) {
                    return "stock less than 10";
                }
                addStock.get(i).setStock(addStock.get(i).getStock() + amountStock);
                return "stock updated";
            }
        }

        if (!productExists) return "product id not correct";
        if (!merchantExists) return "merchant id not correct";
        return "product and merchant not linked";
    }


    public String buyProduct(String userId, String productId, String merchantId) {
        ArrayList<MerchantStock> merchantArray = merchantStockService.getMerchantStroks();
        ArrayList<Product> productArray = productService.getProduct();
        ArrayList<User> userArray = userService.getUser();

        User foundUser = null;
        for (int i = 0; i < userArray.size(); i++) {
            if (userArray.get(i).getId().equalsIgnoreCase(userId)) {
                foundUser = userArray.get(i);
                break;
            }
        }
        if (foundUser == null) return "user id not found";

        Product foundProduct = null;
        for (int i = 0; i < productArray.size(); i++) {
            if (productArray.get(i).getId().equalsIgnoreCase(productId)) {
                foundProduct = productArray.get(i);
                break;
            }
        }
        if (foundProduct == null) return "product id not found";

        MerchantStock foundStock = null;
        for (int i = 0; i < merchantArray.size(); i++) {
            if (merchantArray.get(i).getMechantId().equalsIgnoreCase(merchantId)
                    && merchantArray.get(i).getProductId().equalsIgnoreCase(productId)) {
                foundStock = merchantArray.get(i);
                break;
            }
        }
        if (foundStock == null) return "merchant id not found for this product";

        if (foundStock.getStock() < 1) {
            return "bad request / not in stock";
        }
        if (foundUser.getBalance() < foundProduct.getPrice()) {
            return "bad request / balance < price";
        }

        foundStock.setStock(foundStock.getStock() - 1);
        foundUser.setBalance(foundUser.getBalance() - foundProduct.getPrice());

        return "buy done";
    }


    public String userDiscount(String userId, String productId) {
        ArrayList<Product> productArray = productService.getProduct();
        ArrayList<User> userArray = userService.getUser();

        boolean userIdfound = false;
        boolean productIdfound = false;

        for (int i = 0; i < userArray.size(); i++) {
            if (userArray.get(i).getId().equalsIgnoreCase(userId)) {
                userIdfound = true;
                break;
            }
        }
        if (!userIdfound) {
            return "user id not found";
        }

        for (int i = 0; i < productArray.size(); i++) {
            if (productArray.get(i).getId().equalsIgnoreCase(productId)) {
                productIdfound = true;
                break;
            }
        }

        if (!productIdfound) {
            return "product id not found";
        }


        if (productIdfound && userIdfound) {
            if (userId.startsWith("11")) {
                for (int i = 0; i < productArray.size(); i++) {
                    if (productArray.get(i).getId().equalsIgnoreCase(productId)) {
                        productArray.get(i).setPrice(productArray.get(i).getPrice() * 0.8);
                        return "price updated , discount by 20% for student where id start with 11";


                    }
                }
            }
        }
        return "this user id dont have a student discount";

    }

    public String deleteMerhantByAdmin(String userId, String merchantId) {
        ArrayList<User> users = userService.getUser();
        //merchantService
        String havePermission = " ";
        String founduserId = " ";
        String foundmerchantId = " ";

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equalsIgnoreCase(userId)) {
                founduserId = "true";
                if (users.get(i).getRole().equalsIgnoreCase("Admin")) {
                    havePermission = "true";
                    break;
//                } else return "user have no permeation";
//            } else return "user id not found";
                }
            }
        }

        if (!founduserId.equalsIgnoreCase("true")) {
            return "user id not found";
        }
        if (!havePermission.equalsIgnoreCase("true")) {
            return "user have not permission";
        }
        //تأكدنا من الاي دي وصلاحية المستخدم

        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equalsIgnoreCase(merchantId)) {

                foundmerchantId = "true";
                if (foundmerchantId.equalsIgnoreCase("true")) {
                    merchants.remove(i);
                    return "remove merchants successfully";
                }
            }
        }


        if (!foundmerchantId.equalsIgnoreCase("true")) {
            return "merchants id not found";

        }

        return "";
    }


//    public String buyProdcut(String userId , String productId , String merchantId){
//        ArrayList<MerchantStock> merchantArray =merchantStockService.getMerchantStroks();
//        ArrayList<Product> productArray=productService.getProduct();
//        ArrayList<User>userArray=userService.getUser();
//
//        boolean userIdfound=false;
//        boolean productIdfound=false;
//        boolean merchantIdfound = false;
//        for(int i=0 ; i<merchantArray.size() ; i++){
//            if(merchantArray.get(i).getMechantId().equalsIgnoreCase(merchantId)){
//                merchantIdfound=true;
//            }
//            if(!merchantIdfound){
//                return "merchant id not found";
//            }
//        }
//        for(int i=0 ; i<userArray.size() ; i++){
//            if(userArray.get(i).getId().equalsIgnoreCase(userId)){
//                userIdfound=true;
//            }
//            if(!userIdfound){
//                return "user id not found";
//            }
//        }
//        for(int i=0 ; i<productArray.size() ; i++){
//            if(productArray.get(i).getId().equalsIgnoreCase(productId)){
//                productIdfound=true;
//            }
//            if(!productIdfound){
//                return "prodct id not found";
//            }
//        }
//        if(productIdfound&&userIdfound&&merchantIdfound){//الكل صحيحة داخلها باقي العمليات
//            boolean inStock = false;
//            double blance=0;
//            double price=0;
//            for(int i = 0 ; i<merchantArray.size() ; i++) {
//                if (merchantArray.get(i).getMechantId().equalsIgnoreCase(merchantId) && merchantArray.get(i).getProductId().equalsIgnoreCase(productId) && merchantArray.get(i).getStock() >= 1) {
//                    inStock = true;
//                }
//
//                if (userArray.get(i).getBalance() > 0)
//                    blance = userArray.get(i).getBalance();
//
//                if (productArray.get(i).getPrice() > 0)
//                    price = productArray.get(i).getPrice();
//
//                if (inStock && blance >= price) {
//                    merchantArray.get(i).setStock(merchantArray.get(i).getStock() - 1);//قللنا المخزون
//                    userArray.get(i).setBalance(userArray.get(i).getBalance() - price);//طرحنا قيمة المنتج
//                    return "buy  done";
//                }
//                if (!inStock) {
//                    return "bad request / not in stock";
//                }
//                if (blance < price) {
//                    return "bad request / blance <  price ";
//                }
//
//
//            }
//        }
//
//
//
//
//        return " ";
//    }


//    public String addStock(String productId, String merchantId , int amountStock){
//        ArrayList<MerchantStock> addStock;
//        boolean isProdcteId = false;
//        boolean isMerchantId = false;
//        boolean isStock = false;
//        String message="Not found ";
//  addStock=(merchantStockService.getMerchantStroks());
//
//
//  for(int i=0 ; i<addStock.size() ; i++) {
//      if (addStock.get(i).getProductId().equalsIgnoreCase(productId))
//          isProdcteId = true;
//      if (addStock.get(i).getMechantId().equalsIgnoreCase(merchantId))
//          isMerchantId = true;
//
//      if (addStock.get(i).getStock() + amountStock >= 10)
//          isStock = true;
//              }
//
//
//      if(!isMerchantId){
//          return "merchant id not correct";
//      }
//      if(!isProdcteId){
//          return "product id not correct";
//      }
//      if(!isStock){
//          return "stock not found";
//      }
//
//        if(isMerchantId&&isProdcteId&&isStock) {
//          for(int i=0 ; i<addStock.size() ; i++){
//              if(addStock.get(i).getMechantId().equalsIgnoreCase(merchantId)&&addStock.get(i).getProductId().equalsIgnoreCase(productId)){
//                addStock.get(i).setStock(addStock.get(i).getStock()+amountStock);
//
//                  return  "stock updated";
//              }
//          }
//
//      }
//        return message;
//    }


//
//    public String addStock(String productId, String merchantId, int amountStock) {
//        ArrayList<MerchantStock> addStock = merchantStockService.getMerchantStroks();
//
//        boolean productExists = false;
//        boolean merchantExists = false;
//        boolean matchFound = false;
//
//        for (int i = 0; i < addStock.size(); i++) {
//            boolean sameProduct = addStock.get(i).getProductId().equalsIgnoreCase(productId);
//            boolean sameMerchant = addStock.get(i).getMechantId().equalsIgnoreCase(merchantId);
//
//            if (sameProduct) productExists = true;
//            if (sameMerchant) merchantExists = true;
//
//            // العنصر المطابق للاثنين معًا
//            if (sameProduct && sameMerchant) {
//                matchFound = true;
//
//                if (addStock.get(i).getStock() + amountStock < 10) {
//                    return "total stock less than 10";
//                }
//
//                addStock.get(i).setStock(addStock.get(i).getStock() + amountStock);
//                return "stock updated";
//            }
//        }
//
//        if (!productExists) return "product id not found";
//        if (!merchantExists) return "merchant id not found";
//        if (!matchFound) return "product id or merchant id not found";
//
//        return "unknown error";
//    }


}