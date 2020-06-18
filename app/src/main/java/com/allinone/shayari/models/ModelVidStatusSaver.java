package com.allinone.shayari.models;

public class ModelVidStatusSaver {
    private String Video,Name;

    public ModelVidStatusSaver(String name, String video) {
        Video = video;
        Name = name;
    }

    public void setImage(String image, String name) {
       //Image = image;
        //Name = ;
    }

    public String getVideo() {
        return Video;
    }
    public String getName() {
        return Name;
    }


}
