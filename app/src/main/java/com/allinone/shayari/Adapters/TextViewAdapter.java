package com.allinone.shayari.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.shayari.R;
import com.allinone.shayari.WhatsappService;
import com.allinone.shayari.models.ModeLImageStatus;
import com.allinone.shayari.models.ModeLTextStatus;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TextViewAdapter extends RecyclerView.Adapter<TextViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModeLTextStatus> mData ;

    public TextViewAdapter(Context context, List<ModeLTextStatus> mData) {
        this.mContext = context;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_textstatus,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    final String Status = mData.get(position).getTxtStatus();
    final String timeago= mData.get(position).getTime();


        holder.statustxt.setText(Status);
        holder.statusTittleText.setText(Status);
        holder.time.setText(timeago);

        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            CopyText(mContext,Status+"\n\n https://bit.ly/asPxMyg");
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, Status);
                mContext.startActivity(shareIntent);
            }
        });
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhatsappService.ShareText(mContext,Status+"\n\n https://bit.ly/asPxMyg");
            }
        });

        holder.messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent
                        .putExtra(Intent.EXTRA_TEXT,
                                Status+"\n\n https://bit.ly/asPxMyg");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.orca");
                try {
                   mContext.startActivity(sendIntent);
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(mContext,"Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
                }

            }
        });

        holder.fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent
                        .putExtra(Intent.EXTRA_TEXT,
                                Status+"\n\n https://bit.ly/asPxMyg");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.katana");
                try {
                    mContext.startActivity(sendIntent);
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(mContext,"Please Install Facebook", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //Picasso picasso;
         ImageView copy,share,whatsapp,messenger,fb;
         TextView statustxt,statusTittleText,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            statustxt = (TextView) itemView.findViewById(R.id.txvstatus);
            statusTittleText = (TextView) itemView.findViewById(R.id.txvstatusTitle);
            copy = (ImageView) itemView.findViewById(R.id.copy);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
            share = (ImageView) itemView.findViewById(R.id.share);
            messenger = (ImageView) itemView.findViewById(R.id.messenger);
            fb = (ImageView) itemView.findViewById(R.id.fb);
            time = (TextView) itemView.findViewById(R.id.timeago);

        }
    }
    public static void CopyText(Context mContext,String text){
        ClipboardManager clipboard = (ClipboardManager)
                mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Status", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(mContext,"Text Copied",Toast.LENGTH_SHORT).show();
    }



}
