package com.example.magi.iwish;

/**
 * Created by MAGI on 2017-10-14.
 */

public class Item {
    private String name, cost,memo,date,id, imgUri,donedate;

    public Item(String name, String cost, String memo, String date, String id, String imgUri, String donedate) {
        this.name = name;
        this.cost = cost;
        this.memo = memo;
        this.date = date;
        this.id = id;
        this.imgUri=imgUri;
        this.donedate=donedate;
    }
//setter,getter


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgURI) {
        this.imgUri = imgURI;
    }

    public String getDonedate() {
        return donedate;
    }

    public void setDonedate(String donedate) {
        this.donedate = donedate;
    }
}


