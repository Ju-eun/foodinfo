package com.project.foodinfo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoreinfoActivity extends AppCompatActivity {

    private static final int GET_GALLERY_IMAGE = 200;
    TabLayout tabLayout;
    ViewPager viewPager;
    ListView lv_menu;
    MyAdapter myAdapter;
    EditText storeinfo_et_name;
    EditText storeinfo_et_category;
    ImageView menu_modify;
    Button btn_add;

    Uri selectedImageUri;
    MenuChangeActivity menuChangeActivityDialog;
    MenuAddActivity MenuAddActivityDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeinfo);
        storeinfo_et_name = findViewById(R.id.ed_storename);
        storeinfo_et_category = findViewById(R.id.Storeinfo_et_category);
        btn_add = findViewById(R.id.btn_add);



        myAdapter = new MyAdapter();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String Uid = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child(Uid).child("store_info");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                MemInfo.Store_Info  store_info = dataSnapshot.getValue(MemInfo.Store_Info.class);

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
        //tabLayout.addTab(tabLayout.newTab().setText("위 치"));
        //tabLayout.addTab(tabLayout.newTab().setText("리 뷰"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

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
        btn_add.setOnClickListener(new View.OnClickListener() {           //메뉴 추가 버튼
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }

                MenuAddActivityDialog = new MenuAddActivity(StoreinfoActivity.this, myAdapter.getCount(), myAdapter);//pos 메뉴 최대 크기 필요

                MenuAddActivityDialog.menuAddCallFunction();

//                ((StoreinfoActivity)context).getDialog(menuChangeActivityDialog);

            }


        });


    }

    protected void get_Menu_Image(ImageView menu_modify) {
        // 권한
        this.menu_modify = menu_modify;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            super.onActivityResult(requestCode, resultCode, data);

            selectedImageUri = data.getData();
            menu_modify.setImageURI(selectedImageUri);
            if(menu_modify.getId() == R.id.menu_modify_iv_menu){
                menuChangeActivityDialog.getNewPass(selectedImageUri);
            }
            else{
                MenuAddActivityDialog.getNewPass(selectedImageUri);
            }

        }
    }


    public void getDialog(MenuChangeActivity menuChangeActivityDialog){
        this.menuChangeActivityDialog = menuChangeActivityDialog;
    }
    public void getMyadapter(MyAdapter myAdapter){
        this.myAdapter = myAdapter;
    }

}



