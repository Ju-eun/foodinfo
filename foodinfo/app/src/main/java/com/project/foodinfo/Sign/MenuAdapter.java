package com.project.foodinfo.Sign;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.loader.content.CursorLoader;

import com.project.foodinfo.MyItem;
import com.project.foodinfo.R;

import java.util.ArrayList;


public class MenuAdapter extends BaseAdapter {
    public ArrayList<MyItem> myItems = new ArrayList<MyItem>();
    private ArrayList<MyItem> filteredItemList = myItems;

    EditText et_name;
    EditText et_price;
    ImageView iv_menu_row;

    Context context;
    Context SignContext;;

    public MenuAdapter(Context SignContext) {
        this.SignContext = SignContext;
    }

    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    @Override
    public MyItem getItem(int position) {
        return filteredItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        final ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_row, parent, false);

            holder = new ViewHolder();
            holder.VHmenu_et_name = (EditText) convertView.findViewById(R.id.et_menu_row_name);
            holder.VHmenu_et_price = (EditText) convertView.findViewById(R.id.et_menu_row_price);
            holder.VHmenu_iv_menu = (ImageView) convertView.findViewById(R.id.iv_menu_row);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ref = position;

        et_name = convertView.findViewById(R.id.et_menu_row_name);
        et_price = convertView.findViewById(R.id.et_menu_row_price);
        iv_menu_row = convertView.findViewById(R.id.iv_menu_row);

        final MyItem listViewItem = filteredItemList.get(position);

        holder.VHmenu_et_name.setText(listViewItem.getName());
        holder.VHmenu_et_price.setText(listViewItem.getPrice());
        holder.VHmenu_iv_menu.setImageURI(listViewItem.getMenuImg());

        holder.VHmenu_et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredItemList.get(holder.ref).setName(s.toString());
            }
        });

        holder.VHmenu_et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredItemList.get(holder.ref).setPrice(s.toString());
            }
        });
        holder.VHmenu_iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SignContext instanceof SignActivity) {
                    ((SignActivity) SignContext).get_Menu_Image();
                    ((SignActivity) SignContext).setImageView(holder.VHmenu_iv_menu,position);
                }
            }
        });

        return convertView;
    }

    public void addItem(String name, String price, int num) {
        MyItem mItem = new MyItem();
        if (myItems.size() >= 5) {
            Toast.makeText(context, "최대 값입니다.", Toast.LENGTH_SHORT).show();
        } else {
            mItem.setMenuImg(null);
            mItem.setName(name);
            mItem.setPrice(price);
            mItem.setNum(num);
            myItems.add(mItem);
        }

    }

    public void removeItem() {
        if (myItems.size() <= 1) {
            Toast.makeText(context, "최소 값입니다.", Toast.LENGTH_SHORT).show();
        } else {
            myItems.remove(myItems.size() - 1);
        }
    }

//    public String getPath(Uri uri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);
//
//        Cursor cursor = cursorLoader.loadInBackground();
//        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//
//        cursor.moveToFirst();
//
//        return cursor.getString(index);
//    }


}




