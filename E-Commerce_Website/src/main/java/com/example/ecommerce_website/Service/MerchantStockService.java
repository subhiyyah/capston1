package com.example.ecommerce_website.Service;

import com.example.ecommerce_website.Model.MerchantStock;
import com.example.ecommerce_website.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class MerchantStockService {
    //All Java code
    //All Array list

    ArrayList<MerchantStock> merchantStocks =new ArrayList<>();

    public void addMerchantStocks(MerchantStock merchantStock){
        merchantStocks.add(merchantStock);

    }

    public ArrayList<MerchantStock>getMerchantStroks(){
        return merchantStocks;
    }

    public boolean updateMerchantStock(String id,MerchantStock merchantStock){
        for (int i =0 ; i<merchantStocks.size() ; i++){
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.set(i, merchantStock);
                return true;}

        }return false;}


    public boolean deleteMerchantStock(String id){
        for (int i =0 ; i<merchantStocks.size() ; i++){
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }return false;
    }


}