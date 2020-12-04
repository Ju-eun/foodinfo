package com.project.foodinfo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    MyAdapter myAdapter;
    Fragment_menu_user fragment_menu_user;
    EditText fragment_info_user;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeinfo_foruser);

        storeinfo_et_name = findViewById(R.id.ed_storename_forUser);
        storeinfo_et_category = findViewById(R.id.Storeinfo_et_category_forUser);
        fragment_menu_user = new Fragment_menu_user();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_forUser);
        tabLayout.addTab(tabLayout.newTab().setText("메 뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("정 보"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager_forUser);

        Intent intent = getIntent();
        store_name = intent.getStringExtra("store_name");

        Log.i("AA", store_name);


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



                            Bundle bundle = new Bundle(1);
                            bundle.putParcelable("meminfo_1", Mem_info);
                            fragment_menu_user.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.ll_menulayout_foruser,fragment_menu_user).commit();
                            MenuPagerUserAdapter pagerUserAdapter = new MenuPagerUserAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                            viewPager.setAdapter(pagerUserAdapter);

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
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Bundle bundle1 = new Bundle(1);
        bundle1.putString("str", store_name);
        fragment_menu_user.setArguments(bundle1);
    }

    public void getFragmentAdapter(MyAdapter myAdapter){
        this.myAdapter = myAdapter;

        for (int i = 0; i < Mem_info.getStore_info().getStore_Size(); i++) {

            String img = Mem_info.getStore_info().getStore_menus().get(i).getMenu_img();
            String menu_name = Mem_info.getStore_info().getStore_menus().get(i).getMenu_name();
            String menu_price = Mem_info.getStore_info().getStore_menus().get(i).getMenu_price();
            Log.d("my123", "2");
            Log.d("my123123123", menu_name);
            this.myAdapter.addItem(img, menu_name, menu_price);

            Log.d("abcd1", img+ "\n " +menu_name+ "\n "+menu_price);
            Log.d("abcd2", Mem_info.getStore_info().getStore_name() + "\n " + Mem_info.getStore_info().getStore_menus().get(i).getMenu_name() + "\n" + Mem_info.getStore_info().getStore_menus().get(i).getMenu_img());
        }

        this.myAdapter.notifyDataSetChanged();
    }

    public void getEtMemo(View view){
        this.fragment_info_user = (EditText)view;

        fragment_info_user.setText("가게 오픈 시간 : " +Mem_info.getStore_info().getStore_time()+ "\n" + Mem_info.getStore_info().getStore_memo());
    }
}



