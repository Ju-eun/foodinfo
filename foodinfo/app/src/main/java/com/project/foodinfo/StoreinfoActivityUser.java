package com.project.foodinfo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoreinfoActivityUser extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    EditText storeinfo_et_name;
    EditText storeinfo_et_category;
    MemInfo Mem_info;
    String store_name;
    Fragment_menu_user fragment_menu_user;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeinfo);

        storeinfo_et_name = findViewById(R.id.ed_storename);
        storeinfo_et_category = findViewById(R.id.Storeinfo_et_category);
        fragment_menu_user = new Fragment_menu_user();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("메 뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("정 보"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent intent = getIntent();
        store_name = intent.getStringExtra("store_name");

        getSupportFragmentManager().beginTransaction().replace(R.id.ll_menulayout,fragment_menu_user).commit();

        Bundle bundle = new Bundle(1);
        bundle.putString("name",store_name);
        fragment_menu_user.setArguments(bundle);

        //가게이름과 같은 놈 가져오기
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Mem_info = dataSnapshot.getValue(MemInfo.class);
                    if(Mem_info.getCheck_owner() == 1){
                        if(store_name.equals(Mem_info.getStore_info().getStore_name())){
                            Log.i("ewq", Mem_info.getStore_info().getStore_name());
                            storeinfo_et_name.setText(Mem_info.getStore_info().getStore_name());
                            storeinfo_et_category.setText(Mem_info.getStore_info().getStore_category());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.i("ewq", store_name);

        MenuPagerUserAdapter pagerUserAdapter = new MenuPagerUserAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerUserAdapter);
//        getImages();

//        lv_menu.setOnItemClickListener();
        pagerUserAdapter.notifyDataSetChanged();

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



