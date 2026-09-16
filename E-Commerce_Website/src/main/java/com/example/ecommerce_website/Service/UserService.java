package com.example.ecommerce_website.Service;

import com.example.ecommerce_website.Model.Product;
import com.example.ecommerce_website.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    //All Java code
    //All Array list

    //    private final MerchantService merchantService;
    private final ProductService productService;
    ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);

    }

    public ArrayList<User> getUser() {
        return users;
    }

    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }

        }
        return false;
    }


    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public String UpdateBalance(String id, double amount) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equalsIgnoreCase(id)) {
                double blance = users.get(i).getBalance();
                if (blance + (amount) < 0) {
                    return "the total balanc can not be negative";
                }
                if (blance + (amount) > 0)
                    users.get(i).setBalance(users.get(i).getBalance() + (amount));
                return "blance updated";
            }
        }

        return "id not found";
    }


    //بحيث اذا كان المستخدم ادمن يعرض كل مصفوفة المنتاجات بالتفصيل اما اذا كان عميل عادي يعرض فقط اسم المنتج السعر والكاتيجوري بدون الاي دي
    public ArrayList<Product> ShowProductByPermissions(String userid) {
        ArrayList<Product> products = productService.getProduct();
        ArrayList<Product> productsNoPermission = productService.getProduct();

        boolean foundUser = false;
        boolean isAdmin = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equalsIgnoreCase(userid)) {
                foundUser = true;
                isAdmin = users.get(i).getRole().equalsIgnoreCase("Admin");
                break;
            }
        }
        if (isAdmin && foundUser) {//نرجع القيم كاملة
            return products;
        }

        if (foundUser && !isAdmin) {//نعمل ارري جديدة ونجعل قيم الاي دي مخفية عن اليوزر
            for (int j = 0; j < products.size(); j++) {
                productsNoPermission.get(j).setId("****");
                productsNoPermission.get(j).setCategortyId("****");

            }
            return productsNoPermission;

        }

        if (!foundUser) {
            return null;
        }

        return null;

    }

//ننقلها للميرتشنت عشان الانجكشن
//    public String deleteMerhantByAdmin(String userId, String merchantId) {
//        ArrayList<Merchant> merchants = merchantService.getMerchant();
//        //merchantService
//        String havePermission = " ";
//        String founduserId = " ";
//        String foundmerchantId = " ";
//
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getId().equalsIgnoreCase(userId)) {
//                founduserId = "true";
//                if (users.get(i).getRole().equalsIgnoreCase("Admin")) {
//                    havePermission = "true";
////                } else return "user have no permeation";
////            } else return "user id not found";
//                }
//            }
//        }
//        if (!founduserId.equalsIgnoreCase("true")) {
//            return "user id not found";
//        }
//        if (!havePermission.equalsIgnoreCase("true")) {
//            return "user have not permission";
//        }
//
//
//        if (havePermission.equalsIgnoreCase("true") && founduserId.equalsIgnoreCase("true")) {
//            for (int i = 0; i < merchants.size(); i++) {
//                if (merchants.get(i).getId().equalsIgnoreCase(merchantId)) {
//
//                    foundmerchantId = "true";
//                    if (foundmerchantId.equalsIgnoreCase("true")) {
//
//                        merchantService.deleteMerchant(merchantId);
//                        return "remove merchants successfully";
//                    }
//                } else {
//                    foundmerchantId = "false";
//                    return "merchants id not found";
//
//                }
//            }
//
//        }//end if
//        return "???";
//    }

}//end class