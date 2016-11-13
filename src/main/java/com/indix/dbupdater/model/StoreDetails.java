package com.indix.dbupdater.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by venkat on 13/11/16.
 */
public class StoreDetails {

    private long storeId;

    private List<SellerInfo> sellerInfoList;


    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public List<SellerInfo> getSellerInfoList() {
        return sellerInfoList;
    }

    public void setSellerInfoList(List<SellerInfo> sellerInfoList) {
        this.sellerInfoList = sellerInfoList;
    }

    public void addSeller(SellerInfo sellerInfo){
        if(sellerInfoList==null)
            sellerInfoList=new ArrayList<SellerInfo>();
        sellerInfoList.add(sellerInfo);
    }

    public String toString(){
        return "{ "
                +" storeId: "+storeId
                +", sellerInfoList:"
                +sellerInfoList
                +" }";
    }
}
