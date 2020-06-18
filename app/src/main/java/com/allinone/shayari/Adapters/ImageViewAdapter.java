package com.allinone.shayari.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.shayari.DownloadService;
import com.allinone.shayari.R;
import com.allinone.shayari.WhatsappService;
import com.allinone.shayari.activity.MediaFullScreen;
import com.allinone.shayari.models.ModeLImageStatus;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModeLImageStatus> mData ;

    public ImageViewAdapter(Context context, List<ModeLImageStatus> mData) {
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

      final String url =  mData.get(position).getImage();
        final String name =  mData.get(position).getName();

        Picasso.get().load(mData.get(position).getImage())
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //Picasso picasso;
         ImageView imgStatus,save,share,whatsapp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgStatus = (ImageView) itemView.findViewById(R.id.imgstatus_cont);
            save = (ImageView) itemView.findViewById(R.id.save);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
            share = (ImageView) itemView.findViewById(R.id.share);
        }
    }

}
