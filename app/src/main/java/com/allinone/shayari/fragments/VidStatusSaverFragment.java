package com.allinone.shayari.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.shayari.Adapters.StatusSaverImgAdapter;
import com.allinone.shayari.Adapters.StatusSaverVidAdapter;
import com.allinone.shayari.R;
import com.allinone.shayari.models.ModelImgStatusSaver;
import com.allinone.shayari.models.ModelVidStatusSaver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VidStatusSaverFragment extends Fragment {

    private View view;
    RelativeLayout noItemLayout;
    RecyclerView recyclerView;

    private static final File STATUS_DIRECTORY = new File(
            Environment.getExternalStorageDirectory().toString() + File.separator + "WhatsApp/Media/.Statuses");
    private List<ModelVidStatusSaver> VidList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_img_status_saver, container, false);
        noItemLayout = view.findViewById(R.id.noStatus);

        getImageStatus();

        return view;

    }

    public void getImageStatus() {
        VidList = new ArrayList<ModelVidStatusSaver>();
        if (STATUS_DIRECTORY.exists()) {


            File[] files = STATUS_DIRECTORY.listFiles();

            for (int i = 0; i < files.length; i++) {
                File file = files[i];


                if (file.getName().endsWith(".png")
                        || file.getName().endsWith(".mp4")
                        || file.getName().endsWith(".3gp")) {
                    VidList.add(new ModelVidStatusSaver(file.getName(), file.getAbsolutePath()));

                }
            }


            if (!VidList.isEmpty()) {
                StatusSaverVidAdapter statusSaverImgAdapter = new StatusSaverVidAdapter(getActivity(), VidList);
                RecyclerView recyclerView = view.findViewById(R.id.imgSaverRecycler);
                recyclerView.setAdapter(statusSaverImgAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

            }
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            noItemLayout.setVisibility(View.VISIBLE);
        }
    }


    public interface OnFragmentInteractionListener {
    }
}
