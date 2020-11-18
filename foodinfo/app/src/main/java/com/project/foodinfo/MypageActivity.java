package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MypageActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText ed_id;
    EditText ed_password;
    EditText ed_email;
    EditText ed_email2;
    EditText ed_name;
    RadioButton owner, user;
    MemInfo memInfo = new MemInfo();
    Button modifybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ed_password = findViewById(R.id.ed_pw);
        ed_id = findViewById(R.id.ed_id);
        modifybtn = findViewById(R.id.modify_btn);
        ed_email = findViewById(R.id.ed_email);
        ed_email2 = findViewById(R.id.ed_email2);
        ed_name = findViewById(R.id.ed_name);
        owner = findViewById(R.id.owner);
        user = findViewById(R.id.user);

        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("moble-foodtruck").child("MemInfo").child("123");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MemInfo m = snapshot.getValue(MemInfo.class);
                ed_id.setText(m.getId());
                ed_name.setText(m.getName());
                ed_email.setText(m.getEmail());

                if(m.getCheck_owner() == 0){
                    user.setChecked(true);
                }else{
                    owner.setChecked(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        modifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                memInfo.setId(ed_id.getText().toString());
                memInfo.setName(ed_name.getText().toString());
                memInfo.setPassword(ed_password.getText().toString());
                memInfo.setEmail(ed_email.getText().toString());
                
                if(owner.isChecked()==true){
                    memInfo.setCheck_owner(1);
                }
                else if(user.isChecked()==true){
                    memInfo.setCheck_owner(0);
                }
                myRef.setValue(memInfo);
            }
        });
    }

}