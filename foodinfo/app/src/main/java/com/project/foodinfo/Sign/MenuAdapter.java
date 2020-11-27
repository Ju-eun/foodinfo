package com.project.foodinfo.Sign;

import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.project.foodinfo.MyItem;
import com.project.foodinfo.R;

import java.net.URI;
import java.util.ArrayList;


public class MenuAdapter extends BaseAdapter {
    public  ArrayList<MyItem> myItems = new ArrayList<MyItem>();
    private ArrayList<MyItem> filteredItemList = myItems;

    EditText et_name;
    EditText et_price;
    ImageButton ib_menu_row;
    Uri uri;
    Context context;
    Context SignContext;

    public MenuAdapter(Context SignContext){
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
        final int pos = filteredItemList.get(position).getNum();
        context = parent.getContext();
        final ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_row, parent, false);
            holder = new ViewHolder();
            holder.VHmenu_et_name = (EditText)convertView.findViewById(R.id.et_menu_row_name);
            holder.VHmenu_et_price = (EditText)convertView.findViewById(R.id.et_menu_row_price);
            holder.VHmenu_ib_menu = (ImageButton)convertView.findViewById(R.id.ib_menu_row);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.ref = position;

        et_name = convertView.findViewById(R.id.et_menu_row_name);
        et_price = convertView.findViewById(R.id.et_menu_row_price);
        ib_menu_row = convertView.findViewById(R.id.ib_menu_row);


        final MyItem listViewItem = filteredItemList.get(position);

        holder.VHmenu_et_name.setText(listViewItem.getName());
        holder.VHmenu_et_price.setText(listViewItem.getPrice());

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
        holder.VHmenu_ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SignContext instanceof SignActivity){

                    ((SignActivity)SignContext).get_Menu_Image();

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
//        mItem.setIcon(img);
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


}




