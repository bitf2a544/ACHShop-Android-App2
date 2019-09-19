package com.zeeshanmac.zeeshan.spoofandroidapp.model;

import java.util.List;

public class ParentCellData {
    boolean isOpen = false;
    List<Items> childGroceryItemList;
    String title;

    public  ParentCellData(String title1, boolean isOpen1, List<Items> childGroceryItemList1) {
        title = title1;
        isOpen = isOpen1;
        childGroceryItemList = childGroceryItemList1;

    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Items> getChildGroceryItemList() {
        return childGroceryItemList;
    }

    public void setChildGroceryItemList(List<Items> childGroceryItemList) {
        this.childGroceryItemList = childGroceryItemList;
    }

}
