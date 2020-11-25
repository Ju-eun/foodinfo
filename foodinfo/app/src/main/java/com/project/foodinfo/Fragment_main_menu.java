package com.project.foodinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class Fragment_main_menu extends Fragment {


    MyAdapter myAdapter;
    ListView lv_main_menu;
    String menu_01, menu_02;
    String clicking;

    ImageButton imgbtn_kor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        // Inflate the layout for this fragment
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo");

        imgbtn_kor = ((ImageButton)view.findViewById(R.id.imgbtn_kor));

        clicking = "fucking";

        Bundle bundle = getArguments();

        if(bundle != null)
            clicking = bundle.getString("key");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myAdapter = new MyAdapter();
           //     getId_string = imgbtn_kor.getId();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MemInfo memInfo = dataSnapshot.getValue(MemInfo.class);
                    MemInfo.Store_Info store_info = dataSnapshot.getValue(MemInfo.Store_Info.class);

                    String category = store_info.getStore_category();

                    if (clicking.equals(category)) {
                        menu_01 = snapshot.child("123").child("menu_img").getValue(String.class);
                        menu_02 = snapshot.child("345").child("menu_img").getValue(String.class);

                        myAdapter.addItem(menu_01, "국밥", "1000");
                        // myAdapter.addItem(menu_02, "af", "fald");

                        myAdapter.notifyDataSetChanged();
                        lv_main_menu.setAdapter(myAdapter);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        lv_main_menu = (ListView) view.findViewById(R.id.lv_main_menu);
        return view;
    }

    public void asd() {

    }
}
