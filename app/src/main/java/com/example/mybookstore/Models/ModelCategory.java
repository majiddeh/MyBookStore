package com.example.mybookstore.Models;

public class ModelCategory {
    int id;
    String title_category;
    String image;

    public ModelCategory(int id, String title_category, String image) {
        this.id = id;
        this.title_category = title_category;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_category() {
        return title_category;
    }

    public void setTitle_category(String title_category) {
        this.title_category = title_category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
