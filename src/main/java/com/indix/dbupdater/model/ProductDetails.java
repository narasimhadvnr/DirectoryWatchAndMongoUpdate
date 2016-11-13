package com.indix.dbupdater.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by venkat on 12/11/16.
 */

@Document(collection = "product")
public class ProductDetails {
    // storeProductId,title,upcs,categoryId,storeId,seller,timestamp,price

    @Id
    private String productId;

    private String title;

    private String upcs;

    private long categoryId;

    private Set<StoreDetails> storeDetailsSet;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUpcs() {
        return upcs;
    }

    public void setUpcs(String upcs) {
        this.upcs = upcs;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Set<StoreDetails> getStoreId() {
        return storeDetailsSet;
    }

    public void setStoreDetailsList(Set<StoreDetails> storeDetailsSet) {
        this.storeDetailsSet = storeDetailsSet;
    }

    public Set<StoreDetails> getStoreDetailsSet(){
    	return storeDetailsSet;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addStoreDetails(StoreDetails storeDetails){
        if(storeDetailsSet==null)
            storeDetailsSet.add(storeDetails);

        storeDetailsSet.add(storeDetails);
    }

    @Override
    public String toString(){

        return "{ ProductId:"+productId
                +", Title: "+title
                +", upcs: "+upcs
                +", categoryId: "+categoryId
                +", storyDetails: "+storeDetailsSet
                +" }";
    }
}
