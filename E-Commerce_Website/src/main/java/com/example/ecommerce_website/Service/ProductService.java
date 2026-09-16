package com.example.ecommerce_website.Service;

import com.example.ecommerce_website.Model.Category;
import com.example.ecommerce_website.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    //All Java code
    //All ArrayList

    private final CategoryService categoryService;
    ArrayList<Product> products = new ArrayList<>();

    public void addProducts(Product product) {
        products.add(product);

    }

    public ArrayList<Product> getProduct() {
        return products;
    }

    public boolean updateProduct(String id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }

        }
        return false;
    }


    public boolean deleteProducts(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public String updatePriceByCategoryName(String productId, String categoryId) {

        ArrayList<Category> categoreArray = categoryService.getCategories();
        String categoryFound = "false";
        String category_name = categoryService.getCategoriesNameById(categoryId);
        if(category_name.equalsIgnoreCase("id not found")){
            return "category id not found";
        }else {
            categoryFound="true";
        }


//        for (int i = 0; i < categoreArray.size(); i++) {
//            if (categoreArray.get(i).getId().equalsIgnoreCase(categoryId)) {
//                category_name = categoreArray.get(i).getName();
//                categoryFound = "true";
//                break;
//            }//وصلنا للكاتيجوري الي ابغى على اساس اسمها انقص من سعر المنتج
//
//        }

        if (!categoryFound.equalsIgnoreCase("true")) {
            return "category id not found";
        }

        String productfound = "false";
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(productId)) {
                productfound = "true";
                switch (category_name) {
                    case "Electronics":
                        products.get(i).setPrice(products.get(i).getPrice() * 0.8);
                        return "have 20% discount by electric item";
                    case "Food":
                        products.get(i).setPrice(products.get(i).getPrice() * 0.7);
                        return "have 30% discount by food item";
                    case "Clothing":
                        products.get(i).setPrice(products.get(i).getPrice() * 0.9);
                        return "have 10% discount by clothing";


                }
            }
            if (!productfound.equalsIgnoreCase("true")) {
                return "product id not found";
            }

        }
        return "not match";
    }

    public int getIndexOfProductById(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(productId)) {
                return i;
            }
        }
        return -1;
    }

    public void setProductsPrice(String productId, int price) {
        int indexofProduct = getIndexOfProductById(productId);
        if (indexofProduct > 0) {
            products.get(indexofProduct).setPrice(price);
        }
    }


    public String setProductPriceRangeBycategoryName(String categoryid, String productId, int min, int max, int price) {
        if (min >= max) {
            return "min can not be greater than or equal max";
        }
        if (min == 0 || max == 0) {
            return "min and max can not be zero";
        }

        String categoryName = categoryService.getCategoriesNameById(categoryid);

        int productIndex = getIndexOfProductById(productId);
        if (productIndex == -1) {
            return "productId not found";
        }

        if (categoryName.equalsIgnoreCase("id not found")) {
            return "category id not found";
        }

//        if (!categoryName.equalsIgnoreCase("id not found")) {
        switch (categoryName) {
            case "Electronics":
                if (min > 100 && max < 30000 && price > min && price < max)
                    products.get(productIndex).setPrice(price);
                return "Electronics | set price successfully";

            case "Food":
                if (min > 0 && max < 3000 && price > min && price < max)
                    products.get(productIndex).setPrice(price);
                return "Food | set price successfully";

            case "Clothing":
                if (min > 0 && max < 5000 && price > min && price < max)
                    products.get(productIndex).setPrice(price);
                return "Clothing | set price successfully";


        }
        return " ";
    }


}//end class