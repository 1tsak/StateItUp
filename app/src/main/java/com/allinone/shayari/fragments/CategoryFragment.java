package com.allinone.shayari.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allinone.shayari.Adapters.CategoryViewAdapter;
import com.allinone.shayari.Adapters.ImageViewAdapter;
import com.allinone.shayari.Common.Constants;
import com.allinone.shayari.R;
import com.allinone.shayari.models.ModeLCategoryStatus;
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
public class CategoryFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private List<ModeLCategoryStatus> categoryList;
    private ProgressBar progressBar;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        loadImageList();

        categoryList = new ArrayList<ModeLCategoryStatus>();


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
    private void loadImageList() {
        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) inflater.findViewById(R.id.progressBar);

        //making the progressbar visible
        //progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.getTextCategory+"category="+"category",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


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
                                categoryList.add(new ModeLCategoryStatus(jsonObject.getString("image"),jsonObject.getString("category")));



                            }
                            RecyclerView recyclerView = view.findViewById(R.id.CategoryRecycler);
                            CategoryViewAdapter adapter = new CategoryViewAdapter(getActivity(),categoryList);
                            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
