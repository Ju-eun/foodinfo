package com.project.foodinfo;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreinfoActivity extends AppCompatActivity  {

    TabLayout tabLayout;
    ViewPager viewPager;
    ListView lv_menu;
    MyAdapter myAdapter;
    EditText ed_storename;
    List<File> menuImgList = new ArrayList<>();
    long menuImgCount = 2;

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

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("메 뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("정 보"));
        tabLayout.addTab(tabLayout.newTab().setText("위 치"));
        tabLayout.addTab(tabLayout.newTab().setText("리 뷰"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.pager);
        lv_menu = (ListView)findViewById(R.id.lv_menu);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
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

//    @Override
//    protected void onStop() {
//        super.onStop();
//        menuImgList.clear();
//        menuImgList = null;
//    }
//
//    public void getImages() {
//            File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/menu_images");
//            if(!file.isDirectory()){
//                file.mkdir();
//            }
//
//            menuImgList.addAll(new ArrayList<>(Arrays.asList(file.listFiles())));
//
//            if(menuImgList.size() < menuImgCount){
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//
//                StorageReference storageReference = storage.getReference();
//                downloadmenuImg(storageReference);
//            }
//        }
//        public void downloadmenuImg(StorageReference storageReference){
//            if(menuImgList == null || menuImgList.size() >= menuImgCount) return;
//
//        String fileName = "menu_"+ menuImgList.size() + ".jpg";//menu_0.jpg
//        File fileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/menu_images");
//        final File downloadFile = new File(fileDir,fileName);
//        StorageReference downloadRef = storageReference
//                .child("menu_images/"+"menu_"+menuImgList.size()+".jpg");//menu_images/menu_0.jpg
//        downloadRef.getFile(downloadFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//               menuImgList.add(downloadFile);
//                if(menuImgList.size() < menuImgCount){
//                    downloadmenuImg(storageReference);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }


}
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

        iv_menuImg.setImageResource(myItem.getIcon());
        tv_menuName.setText(myItem.getName());
        tv_price.setText(myItem.getPrice());

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://moble-foodtruck.appspot.com");
        StorageReference storageRef = storage.getReference();

        storageRef.child("menu_imges").child("menu_0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri uri) {
                    Glide.with(context)
                            .load(uri)
                            .into(iv_menuImg);
            }
        });


        return convertView;
    }
    public void addItem(String name, String price){
        MyItem mItem = new MyItem();
//        mItem.setIcon(img);
        mItem.setName(name);
        mItem.setPrice(price);

        myItems.add(mItem);
    }

    class MyItem{

        private int icon;
        private String name;
        private String price;

        public int getIcon(){
            return icon;
        }
        public void  setIcon(int icon){
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
