package com.gmail.muskankataria2408.shaktiworks;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class MonthModel extends RealmObject {

    private String year;

    private String month;

    @PrimaryKey
    private String compositePrimaryKey;

    private RealmList<ItemsModel> itemsModels;

    public MonthModel(String year, String month,String compositePrimaryKey) {
        this.year = year;
        this.month = month;
        this.compositePrimaryKey=compositePrimaryKey;
    }

    public MonthModel(){}
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCompositePrimaryKey() {
        return compositePrimaryKey;
    }

    public void setCompositePrimaryKey(String compositePrimaryKey) {
        this.compositePrimaryKey = compositePrimaryKey;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public RealmList<ItemsModel> getItemsModel() {
        return itemsModels;
    }

    public void setItemsModel(RealmList<ItemsModel> itemsModel) {
        this.itemsModels = itemsModel;
    }

    public void addItemsModel(ItemsModel itemsModel)
    {
        this.itemsModels.add(itemsModel);
    }
}
