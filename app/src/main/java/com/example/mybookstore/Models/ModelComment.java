package com.example.mybookstore.Models;

public class ModelComment {
    String image;
    String user;
    String comment;
    String positive;
    String negative;
    float rating;

    public ModelComment(String user) {
        this.user = user;
    }

    public ModelComment(String image, String user, String comment, String positive, String negative, float rating) {
        this.image = image;
        this.user = user;
        this.comment = comment;
        this.positive = positive;
        this.negative = negative;
        this.rating = rating;
    }

    public ModelComment() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
