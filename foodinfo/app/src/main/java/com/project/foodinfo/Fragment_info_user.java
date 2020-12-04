package com.project.foodinfo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.    EditText fragment_et_time;
 */
public class Fragment_info_user extends Fragment {

    EditText fragment_et_time;
    Context context;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    MemInfo memInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        context = container.getContext();

        view = inflater.inflate(R.layout.fragment_info_user, container, false);

        fragment_et_time = (EditText) view.findViewById(R.id.fragment_et_time);
        ((StoreinfoActivityUser)context).getEtMemo(fragment_et_time);

        return view;
    }
}