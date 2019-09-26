package com.example.mybookstore.Models;

public class ModelBanner {
    int id;
    String image;

    public ModelBanner(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public ModelBanner() {
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
}
