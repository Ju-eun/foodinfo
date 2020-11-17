package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MypageActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("mypage");

         myRef.setValue("안녕");

         myRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 String value = dataSnapshot.getValue(String.class);
                 Log.d("jungmin", "Value is : " + value);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Log.w("jungmin", "Failed to read value.", databaseError.toException());
             }
         });
    }
}