package com.allinone.shayari.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allinone.shayari.Adapters.ImageViewAdapter;
import com.allinone.shayari.Common.Constants;
import com.allinone.shayari.R;
import com.allinone.shayari.models.ModeLImageStatus;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private List<ModeLImageStatus> imgList;
    private ProgressBar progressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerView;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image, container, false);
        loadImageList();

        imgList = new ArrayList<ModeLImageStatus>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImageList();
                //progressBar.setVisibility(View.VISIBLE);
            }
        });


        progressBar = view.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.imgRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


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
    private void loadImageList() {
        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) inflater.findViewById(R.id.progressBar);

        //making the progressbar visible
        //progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.getMediaStatus+"image="+"image",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        mSwipeRefreshLayout.setRefreshing(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        imgList = new ArrayList<ModeLImageStatus>();

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
                                imgList.add(new ModeLImageStatus(jsonObject.getString("path"),jsonObject.getString("name")));



                            }
                            ImageViewAdapter adapter = new ImageViewAdapter(getActivity(),imgList);
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                            recyclerView.setAdapter(adapter);





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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
