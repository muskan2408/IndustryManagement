package com.gmail.muskankataria2408.shaktiworks;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ItemsModel extends RealmObject {

     @PrimaryKey
     private int id;

     private String item;

     private long quantity;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemsModel(String item, long quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    public ItemsModel(){}

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
