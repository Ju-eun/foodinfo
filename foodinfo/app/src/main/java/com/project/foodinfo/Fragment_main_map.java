package com.project.foodinfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.foodinfo.Sign.SignActivity;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */

public class Fragment_main_map extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE_PERMISSION = 1000;
    public MapView mapView;
    public GoogleMap mMap;
    double latitude;
    double longitude;
    String key;
    Store_pos openstore;
    private GpsTracker gpsTracker;
    FirebaseDatabase database;
    DatabaseReference myRef;

    LatLng Myposition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_map, container, false);
        // Inflate the layout for this fragment
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("moble-foodtruck").child("OpenStore");
        gpsTracker = new GpsTracker(getActivity());

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        Myposition = new LatLng(latitude , longitude);

        mapView = (MapView) view.findViewById(R.id.main_mapview);
        mapView.getMapAsync(this);
//        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//액티비티가 처음 생성될 때 실행되는 함수

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ((MainActivity)getActivity()).getmap(mMap,Myposition);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Myposition));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        Log.i("asd", Myposition+"");
        onAddMarker();
        MarkerOptions markerOptions = new MarkerOptions();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.\
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSION);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren()){
                    key = datasnapshot.getKey();

                    openstore = datasnapshot.getValue(Store_pos.class);

                    Log.i("key123",key);

                    Log.i("key123", "x값 " + openstore.getX());
                    Log.i("key123", "y값 " + openstore.getY());

                    LatLng Store_position = new LatLng( Double.valueOf(openstore.getX()),Double.valueOf(openstore.getY()));

                    markerOptions.title(key);
                    markerOptions.position(Store_position);
                    mMap.addMarker(markerOptions);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        // mMap.getUiSettings().setMyLocationButtonEnabled(true);

//        markerOptions.position(seoul);
//        markerOptions.title("서울");
//        markerOptions.snippet("수도");
//        mMap.addMarker(markerOptions);





    }

    public void onAddMarker(){
        // 반경 1KM원
        CircleOptions circle1KM = new CircleOptions().center(Myposition) //원점
                .radius(1000)      //반지름 단위 : m
                .strokeWidth(0f)  //선너비 0f : 선없음
                .fillColor(Color.parseColor("#66999999")); //배경색

        //원추가
        mMap.addCircle(circle1KM);
    }


}