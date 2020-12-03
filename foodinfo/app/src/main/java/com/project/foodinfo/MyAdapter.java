package com.project.foodinfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;
import com.project.foodinfo.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<MyItem> myItems = new ArrayList<>();
    MyItem mItem;
    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return myItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void clear(){
            myItems.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_menu, parent, false);
        }


        ImageView iv_menuImg = (ImageView) convertView.findViewById(R.id.iv_menu);
        TextView tv_menuName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);

//        MyItem myItem = getItem(position);
        mItem = myItems.get(position);


        Log.d("12345", position +" : " +mItem.getName());

        Glide.with(context).
                load(mItem.getImagename()).
                into(iv_menuImg);

        tv_menuName.setText(mItem.getName());
        tv_price.setText(mItem.getPrice());

        return convertView;
    }

    public void addItem(String imageName, String name, String price) {
        mItem = new MyItem();
        mItem.setImagename(imageName);
        mItem.setName(name);
        mItem.setPrice(price);
        myItems.add(mItem);
    }

}
