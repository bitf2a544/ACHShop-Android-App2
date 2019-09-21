package com.zeeshanmac.zeeshan.spoofandroidapp.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Items implements Serializable {


    // String id;
    @Exclude
    String key;
    String itemName;
    int itemQuantity;
    String itemDescription;
    Boolean check;

    public Items() {

    }

    public Items(String id1,
                 String itemName1,
                 int itemQuantity1,
                 String itemDescription1,
                 boolean check1) {
        //  id = id1;
        itemName = itemName1;
        itemQuantity = itemQuantity1;
        itemDescription = itemDescription1;
        check = check1;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Boolean getIsChecked() {
        return check;
    }

    public void setIsChecked(Boolean check) {
        this.check = check;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
