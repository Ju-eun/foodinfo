package com.project.foodinfo;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view;

        view = inflater.inflate(R.layout.fragment_menu, container, false);

        lv_menu = (ListView) view.findViewById(R.id.lv_menu);
        MyAdapter myAdapter = new MyAdapter();



        for(int i = 0;i<10;i++){
            myAdapter.addItem("국밥","1000");
            myAdapter.notifyDataSetChanged();
        }
        lv_menu.setAdapter(myAdapter);




        return view;
    }




}


