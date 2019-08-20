package com.example.mybookstore.Models;

public class ModelBasket {

    private int id;
    private String image;
    private String number;
    private String title;
    private String price;
    private String allPrice;


    public ModelBasket(int id, String image, String number, String title, String price, String allPrice) {
        this.id = id;
        this.image = image;
        this.number = number;
        this.title = title;
        this.price = price;
        this.allPrice = allPrice;
    }

    public ModelBasket() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }
}
