package com.project.foodinfo.Sign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;


<<<<<<< HEAD
public class MenuAdapter  {


=======
import com.project.foodinfo.MyItem;
import com.project.foodinfo.R;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    private ArrayList<MyItem> myItems = new ArrayList<>();
    Context context;
    int itemH;

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

    public int getItemH(){
        return itemH;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_row, parent,false);
        }

        itemH = convertView.getHeight();

        EditText et_name = convertView.findViewById(R.id.et_menu_row_name);
        EditText et_price = convertView.findViewById(R.id.et_menu_row_price);

        MyItem myItem = getItem(position);

        et_name.setText(myItem.getName());
        et_price.setText(myItem.getPrice());

        return convertView;
    }
    public void addItem(String name, String price){
        if(myItems.size() >=5){
            Toast.makeText(context, "최대 값입니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            MyItem mItem = new MyItem();
//        mItem.setIcon(img);
            mItem.setName(name);
            mItem.setPrice(price);

            myItems.add(mItem);
        }

    }
    public void removeItem(){
        if(myItems.size() <= 1 ){
            Toast.makeText(context, "최소 값입니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            myItems.remove(myItems.size()-1);
        }
    }
>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93
}


