package com.project.foodinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
public class Fragment_info extends Fragment {

    EditText fragment_et_time;
    Button fragment_info_memo_btn;
    DatabaseReference myRef;
    MemInfo m;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_info, container, false);

        fragment_et_time = (EditText) view.findViewById(R.id.fragment_et_time);
        fragment_info_memo_btn = (Button)view.findViewById(R.id.fragment_info_memo_btn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = user.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


        myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child(Uid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                m = dataSnapshot.getValue(MemInfo.class);

                fragment_et_time.setText(m.getStore_info().getStore_memo());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fragment_info_memo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),m.getStore_info().getStore_memo() , Toast.LENGTH_SHORT).show();
                myRef.child("store_info").child("store_memo").setValue(fragment_et_time.getText().toString());
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}