package com.allinone.shayari.models;

public class ModeLVideoStatus {
    private String Video,Name;

    public ModeLVideoStatus(String videourl,String name) {
        Video = videourl;
        Name = name;
    }

    public void setVideo(String videourl) {
        Video = videourl;
    }

    public String getVideo() {
        return Video;
    }

    public String getName() {
        return Name;
    }
}
