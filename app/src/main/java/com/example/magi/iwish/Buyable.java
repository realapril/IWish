package com.example.magi.iwish;

/**
 * Created by MAGI on 2017-10-24.
 */

public class Buyable {
    String title;
    String description;
    int image;
    String cost;
    Buyable(String title, String description, int image, String cost)
    {
        this.title=title;
        this.description=description;
        this.image=image;
        this.cost=cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
