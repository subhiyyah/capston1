package com.example.ecommerce_website.Service;

import com.example.ecommerce_website.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
    //all Java code
    //all Array list

    ArrayList<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
//        return category;
    }

    public boolean deleteCategory(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean updateCategories(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.set(i, category);
                return true;
            }

        }
        return false;
    }


    public String getCategoriesNameById(String categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equalsIgnoreCase(categoryId)) {
                return categories.get(i).getName();
            }
        }
        return "id not found";
    }

    public int getIndexOfCategoriesById(String categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equalsIgnoreCase(categoryId)) {
                return i;
            }
        }
        return -1;
    }

}//end class