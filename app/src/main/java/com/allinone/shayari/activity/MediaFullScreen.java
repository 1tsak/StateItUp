package com.allinone.shayari.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.allinone.shayari.DownloadService;
import com.allinone.shayari.R;
import com.allinone.shayari.VideoShare;
import com.allinone.shayari.WhatsappService;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class MediaFullScreen extends AppCompatActivity {

    public String Type,url,name;
    public FloatingActionButton save,share;
    private InterstitialAd interstitialAd;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_media_full_screen);
        Intent intent = getIntent();
        Type = intent.getStringExtra("Type");
        url = intent.getStringExtra("url");
        name = intent.getStringExtra("name");

        save = findViewById(R.id.save);
        share = findViewById(R.id.share);
        // Fb Ads
        interstitialAd = new InterstitialAd(this, "539145000145124_539147300144894");
        interstitialAd.loadAd();
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                interstitialAd.loadAd();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        //adView = new AdView(this, "539145000145124_539872123405745", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        //LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
       // adContainer.addView(adView);

        // Request an ad
       // adView.loadAd();

        if (Type.equals("Image") ){
            setImage();
        }else{setVideo();}
    }

    private void setVideo() {
        VideoView videoView = findViewById(R.id.videoview);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoPath(url);
        videoView.start();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial();
                DownloadService.DownloadVideo(getApplicationContext(),url,name);

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showInterstitial();
                VideoShare videoShare = new VideoShare();
                videoShare.fileName = name;
                videoShare.videoToDownload = url;
                videoShare.type = "normal";
                videoShare.context = MediaFullScreen.this;
                videoShare.execute();
            }
        });
    }

    private void setImage() {
        ImageView imageView = findViewById(R.id.imageview);
        imageView.setVisibility(View.VISIBLE);
        Picasso.get().load(url)
                .into(imageView);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DownloadService.DownloadImage(getApplicationContext(),url,name);

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhatsappService.shareImage(url,getApplicationContext());
            }
        });
    }

    @Override
    protected void onResume() {
        //showInterstitial();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    private void showInterstitial() {
        if(interstitialAd == null || !interstitialAd.isAdLoaded()) {
            return;
        }
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        if(interstitialAd.isAdInvalidated()) {
            return;
        }
        // Show the ad
        interstitialAd.show();
    }
}
