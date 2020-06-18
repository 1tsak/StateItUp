package com.allinone.shayari.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.shayari.DownloadService;
import com.allinone.shayari.R;
import com.allinone.shayari.VideoShare;
import com.allinone.shayari.WhatsappService;
import com.allinone.shayari.activity.MediaFullScreen;
import com.allinone.shayari.models.ModeLVideoStatus;
import com.allinone.shayari.models.ModelImgStatusSaver;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class StatusSaverImgAdapter extends RecyclerView.Adapter<StatusSaverImgAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModelImgStatusSaver> mData ;
    ProgressBar progressBar;

    public StatusSaverImgAdapter(Context context, List<ModelImgStatusSaver> mData) {
        this.mContext = context;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_imagesaver,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final String imagepath = mData.get(position).getImage();
        final String name = mData.get(position).getName();

        final Bitmap image = BitmapFactory.decodeFile(imagepath);

        holder.statusview.setImageBitmap(image);
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File source = new File(imagepath);

                File destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/StatusNShayari/"+name);
                destination.getParentFile().mkdirs();

                try
                {
                    FileUtils.copyFile(source, destination);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                Toast.makeText(mContext,"Saved",Toast.LENGTH_LONG).show();
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(imagepath);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_TEXT, "https://bit.ly/asPxMyg");
                i.putExtra(Intent.EXTRA_STREAM, uri);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mContext.startActivity(Intent.createChooser(i, "Share Image"));
            }
        });

        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(imagepath);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.setPackage("com.whatsapp");
                i.putExtra(Intent.EXTRA_TEXT, "https://bit.ly/asPxMyg");
                i.putExtra(Intent.EXTRA_STREAM, uri);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mContext.startActivity(Intent.createChooser(i, "Share Image"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        //Picasso picasso;
         ImageView statusview,download,share,whatsapp;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            statusview = (ImageView) itemView.findViewById(R.id.imgsaver_cont);
            download = (ImageView) itemView.findViewById(R.id.save);
            share = (ImageView) itemView.findViewById(R.id.share);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
        }
    }



}
