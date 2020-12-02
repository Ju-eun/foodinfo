package com.project.foodinfo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class Fragment_menu extends Fragment {

        ListView lv_menu;
        MyAdapter myAdapter;
        MenuChangeActivity menuChangeActivityDialog;
        Context context;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view;

            context = container.getContext();

            view = inflater.inflate(R.layout.fragment_menu, container, false);

            lv_menu = (ListView) view.findViewById(R.id.lv_menu);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String Uid = user.getUid();

        myAdapter = new MyAdapter();
        lv_menu.setAdapter(myAdapter);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child(Uid).child("store_info");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                MemInfo.Store_Info store_info = snapshot.getValue(MemInfo.Store_Info.class);
                myAdapter.clear();
                for (int i = 0; i < store_info.getStore_Size(); i++) {
                    myAdapter.addItem(store_info.getStore_menus().get(i).getMenu_img()
                            , store_info.getStore_menus().get(i).getMenu_name()
                            , store_info.getStore_menus().get(i).getMenu_price());
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ASDG", "Failed to read value.", error.toException());
            }

        });


        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }

                menuChangeActivityDialog = new MenuChangeActivity(context, position, myAdapter);
                menuChangeActivityDialog.menuChangeCallFunction();
                ((StoreinfoActivity)context).getDialog(menuChangeActivityDialog);

            }
        });

        return view;
    }


}


