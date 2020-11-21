package com.project.foodinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Fragment_review extends Fragment {



    ListView lv_review;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        lv_review = view.findViewById(R.id.lv_review);

        MyAdapter myAdapter = new MyAdapter();
        lv_review.setAdapter(myAdapter);
//        for(int i = 0;i<10;i++){
//            myAdapter.addItem("국밥","1000");
//            myAdapter.notifyDataSetChanged();
//        }
        return view;
    }
}