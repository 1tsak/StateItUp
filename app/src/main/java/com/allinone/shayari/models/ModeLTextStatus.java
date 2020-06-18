package com.allinone.shayari.models;

public class ModeLTextStatus {
    private String TxtStatus;
    private String Time;

    public ModeLTextStatus(String txtstatus,String time) {
        TxtStatus = txtstatus;
        Time = time;
    }

    public void setTxtStatus(String txtStatus) {
        TxtStatus = txtStatus;

    }
    public void setTime(String time) {
        Time = time;

    }
    public String getTxtStatus() {
        return TxtStatus;
    }

    public String getTime() {
        return Time;
    }


}
