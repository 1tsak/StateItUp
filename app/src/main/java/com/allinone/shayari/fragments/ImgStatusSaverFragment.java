package com.allinone.shayari.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.allinone.shayari.Adapters.StatusSaverImgAdapter;
import com.allinone.shayari.R;
import com.allinone.shayari.models.ModeLImageStatus;
import com.allinone.shayari.models.ModelImgStatusSaver;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImgStatusSaverFragment extends Fragment {

    private View view;
    RelativeLayout noItemLayout;
    RecyclerView recyclerView;

    private static final File STATUS_DIRECTORY = new File(
            Environment.getExternalStorageDirectory().toString() + File.separator + "WhatsApp/Media/.Statuses");
    private List<ModelImgStatusSaver> imgList;

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
        imgList = new ArrayList<ModelImgStatusSaver>();
        if (STATUS_DIRECTORY.exists()) {


            File[] files = STATUS_DIRECTORY.listFiles();

            for (int i = 0; i < files.length; i++) {
                File file = files[i];


                if (file.getName().endsWith(".png")
                        || file.getName().endsWith(".jpg")
                        || file.getName().endsWith(".jpeg")
                        || file.getName().endsWith(".gif")
                        || file.getName().endsWith(".bmp")
                        || file.getName().endsWith(".webp")) {
                    imgList.add(new ModelImgStatusSaver(file.getName(), file.getAbsolutePath()));

                }
            }


            if (!imgList.isEmpty()) {
                StatusSaverImgAdapter statusSaverImgAdapter = new StatusSaverImgAdapter(getActivity(), imgList);
                recyclerView = view.findViewById(R.id.imgSaverRecycler);
                recyclerView.setAdapter(statusSaverImgAdapter);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);
                noItemLayout.setVisibility(View.VISIBLE);
            }
        }
    }


    public interface OnFragmentInteractionListener {
    }
}
