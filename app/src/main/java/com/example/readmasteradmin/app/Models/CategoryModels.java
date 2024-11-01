package com.example.readmasteradmin.app.Models;

public class CategoryModels {

    private String title,image,pdf,key;

    public CategoryModels() {
        this.title = title;
    }

    public CategoryModels(String title, String image, String pdf, String key) {
        this.title = title;
        this.image = image;
        this.pdf = pdf;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
