package com.allinone.shayari.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.shayari.DownloadService;
import com.allinone.shayari.R;
import com.allinone.shayari.VideoShare;
import com.allinone.shayari.WhatsappService;
import com.allinone.shayari.activity.MediaFullScreen;
import com.allinone.shayari.models.ModeLVideoStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModeLVideoStatus> mData ;
    ProgressBar progressBar;

    public VideoViewAdapter(Context context, List<ModeLVideoStatus> mData) {
        this.mContext = context;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_imagestatus,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
   final String url = mData.get(position).getVideo();
       final String name = mData.get(position).getName();

        Picasso.get().load(url)
                //.fit()
                //.centerCrop()
                .into(holder.imgStatus);

        holder.imgStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MediaFullScreen.class);
                intent.putExtra("Type","Image");
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                mContext.startActivity(intent);
            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadService.DownloadImage(mContext,url,name);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhatsappService.shareImage(url,mContext);
            }
        });
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhatsappService.shareImageWhatsapp(url,mContext);
            }
        });
       /* Glide.with(mContext).asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.VideoStatus);
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadService.DownloadVideo(mContext,url,name);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoShare videoShare = new VideoShare();
                videoShare.fileName = name;
                videoShare.videoToDownload = url;
                videoShare.type = "normal";
                videoShare.context = mContext;
                videoShare.execute();
            }
        });
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             VideoShare videoShare = new VideoShare();
             videoShare.fileName = name;
             videoShare.videoToDownload = url;
             videoShare.type = "whatsapp";
             videoShare.context = mContext;
             videoShare.execute();
            }
        });
        holder.VideoStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MediaFullScreen.class);
                intent.putExtra("Type","Video");
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                mContext.startActivity(intent);
            }
        });

        */
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //Picasso picasso;
         ImageView VideoStatus,save,share,whatsapp,imgStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgStatus = (ImageView) itemView.findViewById(R.id.imgstatus_cont);
            save = (ImageView) itemView.findViewById(R.id.save);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
            share = (ImageView) itemView.findViewById(R.id.share);

           /* VideoStatus = (ImageView) itemView.findViewById(R.id.videostatus_cont);
            save = (ImageView) itemView.findViewById(R.id.save);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
            share = (ImageView) itemView.findViewById(R.id.share);

            */
        }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        //give YourVideoUrl below
        retriever.setDataSource(videoPath, new HashMap<String, String>());
// this gets frame at 2nd second
        Bitmap image = retriever.getFrameAtTime(2000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
//use this bitmap image
        return image;
    }

}
