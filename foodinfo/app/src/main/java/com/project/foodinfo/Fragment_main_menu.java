package com.project.foodinfo;

import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class Fragment_main_menu extends Fragment {

    ListView lv_main_menu;
    String clicking;
    MyAdapter myAdapter;
    ImageButton imgbtn_kor;
    MemInfo memInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        // Inflate the layout for this fragment
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo");

        imgbtn_kor = ((ImageButton)view.findViewById(R.id.imgbtn_kor));
        myAdapter = new MyAdapter();
        clicking = "";

        Bundle bundle = getArguments();

        if(bundle != null)
            clicking = bundle.getString("key");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                     getId_string = imgbtn_kor.getId();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    memInfo = dataSnapshot.getValue(MemInfo.class);

                    int check = memInfo.getCheck_owner();

                    if (check == 1) {
                        String category = memInfo.getStore_info().getStore_category();
                        if (clicking.equals(category)) {

                            myAdapter.addItem(memInfo.getStore_info().getStore_menus().get(0).getMenu_img()
                                    , memInfo.getStore_info().getStore_name()
                                    , memInfo.getStore_info().getStore_pos().getX() +", " + memInfo.getStore_info().getStore_pos().getY() );

                            Log.d("AA!@#",memInfo.getStore_info().getStore_name() );
                            myAdapter.notifyDataSetChanged();

                        }
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
                Log.d("AA",String.valueOf(position));
                Intent intent = new Intent(getActivity(), StoreinfoActivityUser.class);
                intent.putExtra("store_name", myAdapter.mItem.getName());
                Log.d("AA",myAdapter.mItem.getName());
                startActivity(intent);
            }
        });
        return view;
    }

}