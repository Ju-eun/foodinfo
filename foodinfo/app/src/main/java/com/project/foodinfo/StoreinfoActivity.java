package com.project.foodinfo;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StoreinfoActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ListView lv_menu;
    MyAdapter myAdapter;
    EditText ed_storename;
    RecyclerView recyclerView;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeinfo);
        ed_storename = findViewById(R.id.ed_storename);

        myAdapter = new MyAdapter();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child("123");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MemInfo m = dataSnapshot.getValue(MemInfo.class);

                ed_storename.setText(m.getId());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("메 뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("정 보"));
        tabLayout.addTab(tabLayout.newTab().setText("위 치"));
        tabLayout.addTab(tabLayout.newTab().setText("리 뷰"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        lv_menu = (ListView) findViewById(R.id.lv_menu);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
//        getImages();
//
//        lv_menu.setAdapter(myAdapter);
        pagerAdapter.notifyDataSetChanged();

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

class MyAdapter extends BaseAdapter {
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


        Log.d("12345", position +" : " +mItem.getImageName());

        Glide.with(context).
                load(mItem.getImageName()).
                into(iv_menuImg);

        tv_menuName.setText(mItem.getName());
        tv_price.setText(mItem.getPrice());

        return convertView;
    }

    public void addItem(String imageName, String name, String price) {
        mItem = new MyItem();
//        mItem.setIcon(img);
        mItem.setImagename(imageName);
//        mItem.setImg(img);
        mItem.setName(name);
        mItem.setPrice(price);

        myItems.add(mItem);
    }

    class MyItem {

        private int icon;
        private String name;
        private String price;
        private String imagename;
        private StorageReference img;

        public StorageReference getImg() {
            return img;
        }

        public void setImg(StorageReference img) {
            this.img = img;
        }

        public String getImageName() {
            return imagename;
        }

        public void setImagename(String imagename) {
            this.imagename = imagename;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
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
