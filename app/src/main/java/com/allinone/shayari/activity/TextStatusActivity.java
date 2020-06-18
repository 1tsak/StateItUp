package com.allinone.shayari.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allinone.shayari.Adapters.ImageViewAdapter;
import com.allinone.shayari.Adapters.TextViewAdapter;
import com.allinone.shayari.Common.Constants;
import com.allinone.shayari.fragments.LatestTextStatus;
import com.allinone.shayari.R;
import com.allinone.shayari.fragments.CategoryFragment;
import com.allinone.shayari.fragments.ImageFragment;
import com.allinone.shayari.fragments.MostSharedTextStatus;
import com.allinone.shayari.fragments.VideoFragment;
import com.allinone.shayari.models.ModeLCategoryStatus;
import com.allinone.shayari.models.ModeLImageStatus;
import com.allinone.shayari.models.ModeLTextStatus;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TextStatusActivity extends AppCompatActivity implements LatestTextStatus.OnFragmentInteractionListener {

    private String Category;
    private View view;
    private TabLayout tabLayout;
    private List<ModeLTextStatus> TextStatusList;
    private ProgressBar progressBar;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_status);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        setUpTabLayout();



    }


    private void setUpTabLayout() {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(new LatestTextStatus(), "Latest");
        adapter.add(new MostSharedTextStatus(), "Most Shared");

        viewPager.setAdapter(adapter);
        viewPager.setPageMargin((int) getResources().getDimension(R.dimen.view_pager_gap));
        viewPager.setPageMarginDrawable(R.color.background);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
