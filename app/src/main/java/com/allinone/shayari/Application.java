package com.allinone.shayari;

import com.facebook.ads.AudienceNetworkAds;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
    }
}
