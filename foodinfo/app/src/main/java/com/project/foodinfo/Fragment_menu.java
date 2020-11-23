package com.project.foodinfo;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Fragment_menu extends Fragment {

    ListView lv_menu;
    String menu_01 = "";
    String menu_02 = "";
    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;

        view = inflater.inflate(R.layout.fragment_menu, container, false);

        lv_menu = (ListView) view.findViewById(R.id.lv_menu);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myAdapter = new MyAdapter();
                menu_01 = dataSnapshot.child("123").child("menu_img").getValue(String.class);
                menu_02 = dataSnapshot.child("345").child("menu_img").getValue(String.class);

//                String name = dataSnapshot.child("123").child("name").getValue(String.class);

                Log.d("ASDG", "Value is: " + menu_01);
                Log.d("ASDG", "Value2 is: " + menu_02);
                myAdapter.addItem(menu_01, "국밥", "1000");
                myAdapter.addItem(menu_02, "af", "fald");
                myAdapter.notifyDataSetChanged();
                lv_menu.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ASDG", "Failed to read value.", error.toException());
            }
        });

//
//        storageRef = storage.getReferenceFromUrl(menu_01);


//        httpsReference = storage.getReferenceFromUrl(menu_02);

        return view;
    }


}


