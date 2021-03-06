package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.renderscript.Sampler;
import android.transition.Visibility;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.foodinfo.Sign.SignActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_PERMISSION = 1000;
    private static final String OWNER = "1";
    Toolbar toolbar;
    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    ViewPager main_viewpager;
    ImageButton imgbtn_kor, imgbtn_coffee, imgbtn_cha, imgbtn_gochi, imgbtn_jan, imgbtn_wes;
    ListView lv_main_menu;
    DatabaseReference myRef;
    Switch switch_open;
    Fragment_main_menu fragment_main_menu;
    GoogleMap mMap;
    LatLng latLng;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    NavigationView navi_view;
    String Check_Owner;
    String uid;
    FirebaseAuth firebase;
    long backKeyPressedTime;
    GpsTracker gpsTracker;
    MemInfo memInfo;
    double latitude, longitude;
    Boolean checked_switch = false;
    String store_name;
    DatabaseReference StoreRef;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    TextView main_owner_tv;
    @Override
    public void onBackPressed() {
        //1번째 백버튼 클릭
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르면 꺼집니당~~", Toast.LENGTH_SHORT).show();
        }
        //2번째 백버튼 클릭 (종료)
        else {
            AppFinish();
        }
    }

    //앱종료
    public void AppFinish() {
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebase = FirebaseAuth.getInstance();

        try {
            uid = user.getUid();
        } catch (Exception e) {
            uid = null;
        }


        lv_main_menu = (ListView) findViewById(R.id.lv_main_menu);
        switch_open = findViewById(R.id.switch_open);
        imgbtn_kor = findViewById(R.id.imgbtn_kor);
        imgbtn_cha = findViewById(R.id.imgbtn_cha);
        imgbtn_jan = findViewById(R.id.imgbtn_jan);
        imgbtn_wes = findViewById(R.id.imgbtn_wes);
        imgbtn_coffee = findViewById(R.id.imgbtn_coffee);
        imgbtn_gochi = findViewById(R.id.imgbtn_gochi);
        main_owner_tv = findViewById(R.id.main_owner_tv);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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
        navi_view = findViewById(R.id.nav_view);

        if (uid != null) {
            myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child(uid);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    memInfo = snapshot.getValue(MemInfo.class);
                    Check_Owner = String.valueOf(memInfo.getCheck_owner());
                    if (Check_Owner.equals(OWNER)) {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.seller_menu);
                        switch_open.setVisibility(View.VISIBLE);
                        main_owner_tv.setVisibility(View.VISIBLE);

                        store_name = memInfo.getStore_info().getStore_name();
                        StoreRef = firebaseDatabase.getReference("moble-foodtruck").child("OpenStore");
                        StoreRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot Datasnapshot : snapshot.getChildren()) {

                                    String my_open_Store = Datasnapshot.getKey();

                                    if (store_name.equals(my_open_Store)) {
                                        checked_switch = true;
                                        switch_open.setChecked(true);
                                    } else {
                                        checked_switch = false;
                                        switch_open.setChecked(false);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    } else {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.consumer_menu);
                        switch_open.setVisibility(View.GONE);
                        main_owner_tv.setVisibility(View.GONE);
                    }
                    navigationView = navi_view;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        //네이게이션 화면 설정
        navigationView = navi_view;
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
                    //구글연동(가능하면)
                } else if (id == R.id.mypage) {
                    Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                    startActivity(intent);
                } else if (id == R.id.storeinfo) {
                    Intent intent = new Intent(MainActivity.this, StoreinfoActivity.class);
                    startActivity(intent);
                } else if (id == R.id.logout) {
                    //로그아웃 + 메인 액티비티 새로고침
                    firebase.signOut();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });


        tabLayout = findViewById(R.id.tabLayout);


        tabLayout.addTab(tabLayout.newTab().setText("메 뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("지 도"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(0).setIcon(R.drawable.main_tab_menu);  //아이콘 추가
        tabLayout.getTabAt(1).setIcon(R.drawable.main_tab_map);

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


    }

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


    public void onSwitch(View view) {

        if(memInfo != null){
            if (switch_open.isChecked()) {
                gpsTracker = new GpsTracker(this);
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();

                Store_pos store_pos = new Store_pos();
                store_pos.setImage_url(memInfo.getStore_info().getStore_menus().get(0).getMenu_img());
                store_pos.setCategory(memInfo.getStore_info().getStore_category());
                store_pos.setX(String.valueOf(latitude));
                store_pos.setY(String.valueOf(longitude));
                StoreRef.child(store_name).setValue(store_pos);
            } else {
                StoreRef.child(store_name).removeValue();
            }
        }


//        StoreRef.child("x").setValue(latitude);
//        StoreRef.child("y").setValue(longitude);


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

}

