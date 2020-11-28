package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.project.foodinfo.Sign.MenuAdapter;

import java.security.Permission;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_PERMISSION = 1000;
    Toolbar toolbar;
    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    ViewPager main_viewpager;
    ImageButton imgbtn_kor, imgbtn_coffee, imgbtn_cha, imgbtn_gochi, imgbtn_jan, imgbtn_wes;
    MyAdapter myAdapter;
    String menu_01, menu_02;
    ListView lv_main_menu;
    DatabaseReference myRef;
    Fragment_main_menu fragment_main_menu;
    FusedLocationProviderClient mFusedLocationClient;
    GoogleMap mMap;
    LatLng latLng;

    MapView mapView;

//    PermissionListener permissionListener = new PermissionListener() {
//        @Override
//        public void onPermissionGranted() {
//            initView();
//        }
//
//        @Override
//        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//            Toast.makeText(context, "권한 허용을 하지 않으면 서비스를 이용할 수 없습니다.", Toast.LENGTH_SHORT).show();
//        }
//    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo");
        lv_main_menu = (ListView) findViewById(R.id.lv_main_menu);

        imgbtn_kor = findViewById(R.id.imgbtn_kor);
        imgbtn_cha = findViewById(R.id.imgbtn_cha);
        imgbtn_jan = findViewById(R.id.imgbtn_jan);
        imgbtn_wes = findViewById(R.id.imgbtn_wes);
        imgbtn_coffee = findViewById(R.id.imgbtn_coffee);
        imgbtn_gochi = findViewById(R.id.imgbtn_gochi);

        ((ImageButton) findViewById(R.id.imgbtn_kor)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.imgbtn_cha)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.imgbtn_wes)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.imgbtn_coffee)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.imgbtn_jan)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.imgbtn_gochi)).setOnClickListener(this);

        //툴바 설정
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();  //제목줄 객체 얻어오기
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);//메뉴모양
        mDrawerLayout = findViewById(R.id.drawer_layout);

        //네이게이션 화면 설정
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                int id = item.getItemId();
                String title = item.getTitle().toString();

                if (id == R.id.login) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.connection) {
                }
                return true;
            }
        });


        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("메 뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("지 도"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        main_viewpager = findViewById(R.id.main_viewpager);

        MainTabPagerAdapter pagerAdapter = new MainTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        main_viewpager.setAdapter(pagerAdapter);

        pagerAdapter.notifyDataSetChanged();

        main_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                main_viewpager.setCurrentItem(tab.getPosition());
                //TODO : tab 상태가 선택 상태로 변경
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //TODO : tab의 상태가 선택되지 않음으로 변경.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //TODO : 이미 선택된 tab이 다시
            }
        });

//        checkPermission();
    }
//    private void checkPermission(){
//        if(Build.VERSION.SDK_INT>=23){
//            TedPermission.with(context)
//                    .setPermissionListener(permissionListener)
//                    .setRationaleMessage("앱을 이용하기 위해서는 접근 권한이 필요합니다.")
//                    .setDeniedMessage("앱에서 요구하는 권한설정이 필요합니다...\n [설정]>[권한]에서 사용으로 활성화 해주세요")
//                    .setPermissions(new String[]{
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION
//                    })
//                    .check();
//        }else{
//            initView();
//        }
//    }
//    private void initView(){
//        if(findViewById(R.id.main_fragment_map)!=null){
//            Fragment fragment = new Fragment_main_map();
//            fragment.setArguments(getIntent().getExtras());
//
//        }

    //    }
    public void getmap(GoogleMap mMap, LatLng latLng) {
        this.mMap = mMap;
        this.latLng = latLng;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Log.d("AA", "클릭해부럿졍");
        fragment_main_menu = new Fragment_main_menu();
        int b = imgbtn_kor.getId();
        int a = v.getId();
        Bundle bundle = new Bundle(1);
        if (a == imgbtn_kor.getId()) {
            bundle.putString("key", "한식");
        } else if (a == imgbtn_cha.getId()) {
            bundle.putString("key", "중식");
        } else if (a == imgbtn_jan.getId()) {
            bundle.putString("key", "일식");
        } else if (a == imgbtn_wes.getId()) {
            bundle.putString("key", "양식");
        } else if (a == imgbtn_coffee.getId()) {
            bundle.putString("key", "커피");
        } else if (a == imgbtn_gochi.getId()) {
            bundle.putString("key", "꼬치");
        }
        fragment_main_menu.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, fragment_main_menu).commit();


//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String test1 = "jongmin";
//                    MemInfo m = dataSnapshot.getValue(MemInfo.class);
//                    String test = m.getName();
//                    if (test.equals(test1)) {
//
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                }
                Log.d("asd3", "3");
            mMap.setMyLocationEnabled(true);
//            mMap.getUiSettings().setMyLocationButtonEnabled(true);
                Log.d("asd4", "4");
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        }
    }

}

class MainTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MainTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_main_menu main_menu = new Fragment_main_menu();
                return main_menu;
            case 1:
                Fragment_main_map main_map = new Fragment_main_map();
                return main_map;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

//                myRef.addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(@NonNull DataSnapshot snapshot) {
//        myAdapter = new MyAdapter();
//        menu_01 = snapshot.child("123").child("menu_img").getValue(String.class);
//        menu_02 = snapshot.child("345").child("menu_img").getValue(String.class);
//
//        myAdapter.addItem(menu_01, "국밥", "1000");
//        myAdapter.addItem(menu_02, "af", "fald");
//        myAdapter.addItem(menu_01, "국밥", "1000");
//        myAdapter.addItem(menu_02, "af", "fald");
//
//        myAdapter.notifyDataSetChanged();
//        lv_main_menu.setAdapter(myAdapter);

