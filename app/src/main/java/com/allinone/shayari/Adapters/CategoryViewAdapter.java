package com.allinone.shayari.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.shayari.R;
import com.allinone.shayari.activity.TextStatusActivity;
import com.allinone.shayari.models.ModeLCategoryStatus;
import com.allinone.shayari.models.ModeLImageStatus;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModeLCategoryStatus> mData ;

    public CategoryViewAdapter(Context context, List<ModeLCategoryStatus> mData) {
        this.mContext = context;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_category,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {



        Picasso.get().load(mData.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.categoryView);

        holder.categoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catgry = mData.get(position).getCategory();
                Intent intent = new Intent(mContext, TextStatusActivity.class);
                intent.putExtra("Category",catgry);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //Picasso picasso;
         ImageView categoryView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryView = itemView.findViewById(R.id.categoryimg);
        }
    }

}
