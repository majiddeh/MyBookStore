package com.example.mybookstore.Models;

public class ModelSearch {

    private int id;
    private String image;
    private String title;
    private String visit;
    private String price;
    private String lable;
    private String offPrice;
    private String desc;

    public ModelSearch() {
    }

    public ModelSearch(int id, String image, String title, String visit, String price, String lable, String offPrice, String desc) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.visit = visit;
        this.price = price;
        this.lable = lable;
        this.offPrice = offPrice;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getOffPrice() {
        return offPrice;
    }

    public void setOffPrice(String offPrice) {
        this.offPrice = offPrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
