package com.allinone.shayari.models;

public class ModeLCategoryStatus {
    private String Image;
    private String Category;

    public ModeLCategoryStatus(String imageurl,String category) {
        Image = imageurl;
        Category = category;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public String getCategory() {
        return Category;
    }
}
