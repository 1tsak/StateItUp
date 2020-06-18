package com.allinone.shayari.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.allinone.shayari.R;
import com.allinone.shayari.fragments.CategoryFragment;
import com.allinone.shayari.fragments.ImgStatusSaverFragment;
import com.allinone.shayari.fragments.LatestTextStatus;
import com.allinone.shayari.fragments.MostSharedTextStatus;
import com.allinone.shayari.fragments.VidStatusSaverFragment;
import com.allinone.shayari.models.ModeLTextStatus;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class StatusSaverActivity  extends AppCompatActivity implements ImgStatusSaverFragment.OnFragmentInteractionListener {

    private String Category;
    private View view;
    private TabLayout tabLayout;
    private List<ModeLTextStatus> TextStatusList;
    private ProgressBar progressBar;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_saver);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        setUpTabLayout();



    }


    private void setUpTabLayout() {
        StatusSaverActivity.ViewPagerAdapter adapter = new StatusSaverActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(new ImgStatusSaverFragment(), "Image Status");
        adapter.add(new VidStatusSaverFragment(),"Video Status");

        viewPager.setAdapter(adapter);
        viewPager.setPageMargin((int) getResources().getDimension(R.dimen.view_pager_gap));
        viewPager.setPageMarginDrawable(R.color.background);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
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
            return titleList.get(position);
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
}
