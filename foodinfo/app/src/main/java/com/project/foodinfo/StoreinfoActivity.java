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
import android.widget.Spinner;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    EditText storeinfo_et_name;
    EditText storeinfo_et_category;

    RecyclerView recyclerView;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeinfo);
        storeinfo_et_name = findViewById(R.id.ed_storename);
        storeinfo_et_category = findViewById(R.id.Storeinfo_et_category);




        myAdapter = new MyAdapter();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String Uid = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child(Uid).child("store_info");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                MemInfo.Store_Info  store_info = new MemInfo.Store_Info();

                storeinfo_et_name.setText(store_info.getStore_name());
                storeinfo_et_category.setText(store_info.getStore_category());
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

        MenuPagerAdapter pagerAdapter = new MenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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



