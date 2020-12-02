package com.project.foodinfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Fragment_menu_user extends Fragment {

    ListView lv_menu;
    MyAdapter myAdapter;
    Context context;
    String store_name;
    MemInfo memInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        context = container.getContext();

        view = inflater.inflate(R.layout.fragment_menu_user, container, false);

        lv_menu = (ListView) view.findViewById(R.id.lv_menu_storeinfo_user);


        Bundle bundle = getArguments();
        if (bundle != null) {
            store_name = bundle.getString("name");
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo");
        myAdapter = new MyAdapter();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                memInfo = snapshot.getValue(MemInfo.class);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                     dataSnapshot.child("store_info");

                    if (memInfo.getCheck_owner() == 1) {

                        Log.i("abcd", store_name + "크크");

                        if (store_name.equals(memInfo.getStore_info().getStore_name())) {
                            for (int i = 0; i < memInfo.getStore_info().getStore_Size(); i++) {
                                myAdapter.addItem(memInfo.getStore_info().getStore_menus().get(i).getMenu_img()
                                        , memInfo.getStore_info().getStore_menus().get(i).getMenu_name()
                                        , memInfo.getStore_info().getStore_menus().get(i).getMenu_price());
                                Log.d("AA12", memInfo.getStore_info().getStore_name() + " " + memInfo.getStore_info().getStore_menus().get(i).getMenu_name());
                            }
                            lv_menu.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ASDG", "Failed to read value.", error.toException());
            }

        });

        return view;
    }


}


