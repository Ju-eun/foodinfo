package com.project.foodinfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class Fragment_main_menu extends Fragment {
    final int GPS_ENABLE_REQUEST_CODE = 200;
    ListView lv_main_menu;
    String clicking;
    MyAdapter myAdapter;
    ImageButton imgbtn_kor;
    Geocoder g;
    Store_pos store_pos = new Store_pos();
    List<Address> address=null;
    String nowAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        // Inflate the layout for this fragment
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("OpenStore");

        imgbtn_kor = ((ImageButton) view.findViewById(R.id.imgbtn_kor));
        myAdapter = new MyAdapter();
        clicking = "";

        g = new Geocoder(getActivity().getApplicationContext());
        Bundle bundle = getArguments();

        if (bundle != null)
            clicking = bundle.getString("key");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                     getId_string = imgbtn_kor.getId();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String storename = dataSnapshot.getKey();
                    store_pos = dataSnapshot.getValue(Store_pos.class);


                    String category = store_pos.getCategory();
                    if (clicking.equals(category)) {
                        try {
                            address = g.getFromLocation(Double.valueOf(store_pos.getX()),Double.valueOf(store_pos.getY()),1);
                            String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                            nowAddress  = currentLocationAddress;
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("test","입출력오류");
                        }
                        if(address!=null){
                            if(address.size()==0){
                                Toast.makeText(getActivity(), "주소찾기 오류", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.d("찾은 주소",address.get(0).toString());
                            }
                        }
                        myAdapter.addItem(store_pos.getImage_url()
                                , storename
                                , nowAddress);



//                        Log.d("AA!@#", memInfo.getStore_info().getStore_name());
                        myAdapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        lv_main_menu = (ListView) view.findViewById(R.id.lv_main_menu);
        lv_main_menu.setAdapter(myAdapter);
        lv_main_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("AA", String.valueOf(position));
                Intent intent = new Intent(getActivity(), StoreinfoActivityUser.class);
                intent.putExtra("store_name", myAdapter.getItem(position).getName());

                Log.d("AA", myAdapter.getItem(position).getName());

                startActivity(intent);
            }
        });
        return view;
    }

}