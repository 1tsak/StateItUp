package com.allinone.shayari.models;

public class ModeLImageStatus {
    private String Image,Name;

    public ModeLImageStatus(String imageurl,String name) {
        Image = imageurl;
        Name = name;
    }

    public void setImage(String image, String name) {
       //Image = image;
        //Name = ;
    }

    public String getImage() {
        return Image;
    }
    public String getName() {
        return Name;
    }


}
