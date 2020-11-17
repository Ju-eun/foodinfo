package com.project.foodinfo;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

class MyAdapter extends BaseAdapter{
    private ArrayList<MyItem> myItems = new ArrayList<>();

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_menu,parent,false);
        }

        ImageView iv_menuImg = (ImageView)convertView.findViewById(R.id.iv_menu);
        TextView tv_menuName = (TextView)convertView.findViewById(R.id.tv_name);
        TextView tv_price = (TextView)convertView.findViewById(R.id.tv_price);

        MyItem myItem = getItem(position);

        iv_menuImg.setImageDrawable(myItem.getIcon());
        tv_menuName.setText(myItem.getName());
        tv_price.setText(myItem.getPrice());

        return convertView;
    }
    public void addItem(Drawable img,String name,String price){
        MyItem mItem = new MyItem();

        mItem.setIcon(img);
        mItem.setName(name);
        mItem.setPrice(price);

        myItems.add(mItem);
    }
}

class TabPagerAdapter extends FragmentStatePagerAdapter {

    private  int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount=tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case  0:
                Fragment_menu menu = new Fragment_menu();
                return menu;
            case 1:
                Fragment_info info = new Fragment_info();
                return info;
            case 2:
                Fragment_map map = new Fragment_map();
                return map;
            case 3:
                Fragment_review review = new Fragment_review();
                return review;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

class MyItem{

   private Drawable icon;
   private String name;
   private String price;

   public  Drawable getIcon(){
       return icon;
   }
   public void  setIcon(Drawable icon){
       this.icon = icon;
   }
   public String getName(){
       return name;
   }
   public void setName(String name){
       this.name = name;
   }
   public String getPrice(){
       return price;
   }
   public void setPrice(String price){
       this.price = price;
   }
}

public class storeinfo extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    ListView lv_menu;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeinfo);
//        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,items);
//        adapter_menu = new ArrayAdapter(this, R.layout.row_menu,R.id.tv_name,items);





        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("메 뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("정 보"));
        tabLayout.addTab(tabLayout.newTab().setText("위 치"));
        tabLayout.addTab(tabLayout.newTab().setText("리 뷰"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.pager);


        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}