package com.project.foodinfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    ListView lv_menu_storeinfo_user;
    MyAdapter myAdapter;
    Context context;
    String store_name;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    String[] strasdasd = {"123", "12"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        context = container.getContext();

        view = inflater.inflate(R.layout.fragment_menu_user, container, false);

        lv_menu_storeinfo_user = (ListView) view.findViewById(R.id.lv_menu_storeinfo_user);



        myAdapter = new MyAdapter();

        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strasdasd);

        lv_menu_storeinfo_user.setAdapter(myAdapter);


        Bundle bundle = getArguments();
        if (bundle != null) {
            store_name = bundle.getString("name");
            Log.d("abcd", store_name);


        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MemInfo memInfo = dataSnapshot.getValue(MemInfo.class);
                    if (memInfo.getCheck_owner() == 1) {
                        Log.d("abcd1", memInfo.getStore_info().getStore_name());
                       // Log.d("abcd1store", store_name);
                        if (store_name.equals(memInfo.getStore_info().getStore_name())) {
                            for (int i = 0; i < memInfo.getStore_info().getStore_Size(); i++) {

                                myAdapter.addItem(memInfo.getStore_info().getStore_menus().get(i).getMenu_img()
                                        , memInfo.getStore_info().getStore_menus().get(i).getMenu_name()
                                        , memInfo.getStore_info().getStore_menus().get(i).getMenu_price());

                                myAdapter.notifyDataSetChanged();

                                Log.d("abcd2", memInfo.getStore_info().getStore_name() + "\n " + memInfo.getStore_info().getStore_menus().get(i).getMenu_name() + "\n" + memInfo.getStore_info().getStore_menus().get(i).getMenu_img());

                            }

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
        return view;
    }
}


