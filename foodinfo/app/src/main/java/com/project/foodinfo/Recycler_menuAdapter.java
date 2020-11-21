package com.project.foodinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Recycler_menuAdapter extends RecyclerView.Adapter<Recycler_menuAdapter.RecyclerHolder> {

    private ArrayList<MyItem> arrayList;
    private Context context;

    public Recycler_menuAdapter(ArrayList<MyItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu,parent,false);
        RecyclerHolder holder = new RecyclerHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getMenuImg())
                .into(holder.iv_menu);
        holder.tv_menuName.setText(arrayList.get(position).getName());
        holder.tv_price.setText(arrayList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView iv_menu;
        TextView tv_menuName;
        TextView tv_price;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_menu = itemView.findViewById(R.id.iv_menu);
            this.tv_menuName = itemView.findViewById(R.id.tv_name);
            this.tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
