package com.allinone.shayari.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allinone.shayari.Adapters.ImageViewAdapter;
import com.allinone.shayari.Adapters.TextViewAdapter;
import com.allinone.shayari.Common.Constants;
import com.allinone.shayari.R;
import com.allinone.shayari.TimeAgo;
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
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestTextStatus extends Fragment {

    private View view;
    private String Category;
    private TabLayout tabLayout;
    private List<ModeLTextStatus> TextStatusList;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    SwipeRefreshLayout mSwipeRefreshLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_latest_text_status, container, false);
        loadStatusList();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadStatusList();
               // progressBar.setVisibility(View.VISIBLE);
            }
        });

        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        Category = intent.getStringExtra("Category");

        loadStatusList();

        TextStatusList = new ArrayList<ModeLTextStatus>();


        progressBar = view.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);





        /*tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);

        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragments(new PlayFragment());
        pagerAdapter.addFragments(new LiveFragment());
        pagerAdapter.addFragments(new ResultFragment());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            tabLayout.getTabAt(0).setText("PLAY");
            tabLayout.getTabAt(1).setText("LIVE");
            tabLayout.getTabAt(2).setText("RESULT");
        } }*/

        return view;






    }
    private void loadStatusList() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.getTextStatus+"latestcategory="+Category,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        mSwipeRefreshLayout.setRefreshing(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        TextStatusList = new ArrayList<ModeLTextStatus>();


                        try {
                            //getting the whole json object from the response
                            JSONArray imagearray = new JSONArray(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            //JSONArray heroArray = array.getJSONArray("image");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < imagearray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject jsonObject = imagearray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                TextStatusList.add(new ModeLTextStatus(jsonObject.getString("status"), TimeAgo.covertTimeToText(jsonObject.getString("time"))));




                            }
                            RecyclerView recyclerView = view.findViewById(R.id.TextRecycler);
                            TextViewAdapter adapter = new TextViewAdapter(getActivity(),TextStatusList);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setItemViewCacheSize(20);
                            recyclerView.setDrawingCacheEnabled(true);
                            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
