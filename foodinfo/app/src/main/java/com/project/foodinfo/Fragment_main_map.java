//package com.project.foodinfo;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the  factory method to
// * create an instance of this fragment.
// */
//public class Fragment_main_map extends Fragment implements OnMapReadyCallback {
//
//    GoogleMap mMap;
//    MapView mapView;
//    View mView;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main_map, container, false);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        MapsInitializer.initialize(getContext());
//        mMap=googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//    }
//
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mapView = mView.findViewById(R.id.main_mapview);
//        if (mapView != null) {
//            mapView.onCreate(null);
//            mapView.onResume();
//            mapView.getMapAsync(this);
//        }
//    }
//
//
//}