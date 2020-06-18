package com.allinone.shayari.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.shayari.R;
import com.allinone.shayari.WhatsappService;
import com.allinone.shayari.models.ModelImgStatusSaver;
import com.allinone.shayari.models.ModelVidStatusSaver;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StatusSaverVidAdapter extends RecyclerView.Adapter<StatusSaverVidAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModelVidStatusSaver> mData ;
    ProgressBar progressBar;

    public StatusSaverVidAdapter(Context context, List<ModelVidStatusSaver> mData) {
        this.mContext = context;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_vidsaver,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final String videopath = mData.get(position).getVideo();
        final String name = mData.get(position).getName();

        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videopath, MediaStore.Video.Thumbnails.MICRO_KIND);

        holder.statusview.setImageBitmap(thumbnail);

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File source = new File(videopath);

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
                Uri uri = Uri.parse(videopath);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("video/*");
                i.putExtra(Intent.EXTRA_TEXT, "https://bit.ly/asPxMyg");
                i.putExtra(Intent.EXTRA_STREAM, uri);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mContext.startActivity(Intent.createChooser(i, "Share Image"));
            }
        });
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(videopath);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("video/*");
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

            statusview = (ImageView) itemView.findViewById(R.id.vidsaver_cont);
            download = (ImageView) itemView.findViewById(R.id.save);
            share = (ImageView) itemView.findViewById(R.id.share);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
        }
    }



}
