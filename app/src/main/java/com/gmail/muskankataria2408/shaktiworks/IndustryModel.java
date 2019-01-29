package com.gmail.muskankataria2408.shaktiworks;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class IndustryModel extends RealmObject {

    @PrimaryKey
    int id;

   private String name;

     private String collaborationMonth;

     private  String collaborationYear;

    public IndustryModel(String name, String collaborationMonth, String collaborationYear) {
        this.name = name;
        this.collaborationMonth = collaborationMonth;
        this.collaborationYear = collaborationYear;
    }

    public IndustryModel(){

    }

    private RealmList<MonthModel> monthModel;


    public RealmList<MonthModel> getMonthModel() {
        return monthModel;
    }

    public void setMonthModel(RealmList<MonthModel> monthModel) {
        this.monthModel = monthModel;
    }

    public String getCollaborationMonth() {
        return collaborationMonth;
    }

    public void setCollaborationMonth(String collaborationMonth) {
        collaborationMonth = collaborationMonth;
    }

    public String getCollaborationYear() {
        return collaborationYear;
    }

    public void setCollaborationYear(String collaborationYear) {
        collaborationYear = collaborationYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   public void addMonthModel(MonthModel month){
        this.monthModel.add(month);
   }


}
