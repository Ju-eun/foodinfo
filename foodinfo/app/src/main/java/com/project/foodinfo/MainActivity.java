package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    ViewPager main_viewpager;

   // MyAdapter myAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바 설정
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();  //제목줄 객체 얻어오기
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);//메뉴모양
        mDrawerLayout=findViewById(R.id.drawer_layout);

        //네이게이션 화면 설정
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                int id = item.getItemId();
                String title = item.getTitle().toString();

                if(id == R.id.login){
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.connection){
                }
                return true;
            }
        });

   //     myAdapter = new MyAdapter();

//        tabLayout = findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("메 뉴"));
//        tabLayout.addTab(tabLayout.newTab().setText("지 도"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        main_viewpager = findViewById(R.id.main_viewpager);
//
//        MainTabPagerAdapter pagerAdapter = new MainTabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
//        main_viewpager.setAdapter(pagerAdapter);

//
//        lv_menu.setAdapter(myAdapter);
       // pagerAdapter.notifyDataSetChanged();

//        main_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                main_viewpager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        //lv_menu = (ListView)findViewById(R.id.lv_menu);
//        lv_menu.setAdapter(myAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case android.R.id.home:{
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            }
        }
        return super.onOptionsItemSelected(item);


    }



}

//class MainTabPagerAdapter extends FragmentStatePagerAdapter {
//
//    private  int tabCount;
//    public MainTabPagerAdapter(FragmentManager fm, int tabCount){
//        super(fm);
//        this.tabCount=tabCount;
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//
//        switch (position){
//            case  0:
//                Fragment_main_menu main_menu = new Fragment_main_menu();
//                return main_menu;
//            case 1:
//                Fragment_main_map main_map = new Fragment_main_map();
//                return main_map;
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return tabCount;
//    }
//}

