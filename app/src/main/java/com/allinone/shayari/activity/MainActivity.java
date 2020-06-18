package com.allinone.shayari.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allinone.shayari.R;
import com.allinone.shayari.fragments.CategoryFragment;
import com.allinone.shayari.fragments.ImageFragment;
import com.allinone.shayari.fragments.LatestTextStatus;
import com.allinone.shayari.fragments.VideoFragment;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LatestTextStatus.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private InterstitialAd interstitialAd;
    private AdView adView;
    private FirebaseAnalytics mFirebaseAnalytics;
    boolean doubleBackToExitPressedOnce = false;
    private int[] tabIcons = {
            R.drawable.ic_icons8_news,
            R.drawable.ic_favorite_black_24dp
    };

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
           // Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //check all needed permissions together
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
        setContentView(R.layout.activity_main);

        initialiseViews();
        setUpTabLayout();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    // Fb Ads


        adView = new AdView(this, "539145000145124_539145376811753", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();

    }

    public void initialiseViews() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
    private void setUpTabLayout() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(new LatestTextStatus(), "TextStatus");
        adapter.add(new ImageFragment(), "Image Status");
        adapter.add(new VideoFragment(), "Video status");
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin((int) getResources().getDimension(R.dimen.view_pager_gap));
        viewPager.setPageMarginDrawable(R.color.background);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[1]);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter implements CategoryFragment.OnFragmentInteractionListener {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> titleList = new ArrayList<>();



        private ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void add(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void onFragmentInteraction(Uri uri) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public NestedScrollView.OnScrollChangeListener  onScrollListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Context context = getApplicationContext();
        String appurl = "https://play.google.com/store/apps/details?id="+getPackageName();
        int id = item.getItemId();
        if (id == R.id.rate){

            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(appurl)));
        }
        if (id == R.id.privacy_policy){

            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://funnyjokesanu.blogspot.com/p/privacy-policy-shayari-status-dp-status.html?m=1")));
        }

        if (id == R.id.shareApp){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, appurl);
            startActivity(shareIntent);
        }
        if (id == R.id.wastatus){
            startActivity(new Intent(MainActivity.this,StatusSaverActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
    //    showInterstitial();
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
