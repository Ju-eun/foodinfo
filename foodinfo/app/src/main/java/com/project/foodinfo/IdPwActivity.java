package com.project.foodinfo;

import android.app.*;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


@SuppressWarnings("deprecation")

public class IdPwActivity extends TabActivity implements View.OnClickListener {
    String key;
    Info value;
    Button btn_1;
    EditText edt_name, edt_email1;
    DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idpw);

        edt_name = findViewById(R.id.edt_name);
        edt_email1 = findViewById(R.id.edt_email1);

        btn_1 = ((Button) findViewById(R.id.btn_1));
        btn_1.setOnClickListener(this);
        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecID =
                tabHost.newTabSpec("ID").setIndicator("아이디 찾기");

        TabHost.TabSpec tabSpecPW =
                tabHost.newTabSpec("PW").setIndicator("비밀번호 찾기");

        tabSpecID.setContent(R.id.아이디찾기);
        tabSpecPW.setContent(R.id.비밀번호찾기);

        tabHost.addTab(tabSpecID);
        tabHost.addTab(tabSpecPW);

        tabHost.setCurrentTab(0);


    }

    @Override
    public void onClick(View v) {

        Ref = FirebaseDatabase.getInstance().getReference("moble-foodtruck").child("MemInfo");
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {

                        key = snapshot.getKey();
                        value = snapshot.getValue(Info.class);

                        Log.d("AA", "key : " + key);
                        Log.d("AA", value.name);
                        Log.d("AA", value.email);
                        Log.d("AA", edt_name.getText().toString());
                        Log.d("AA", edt_email1.getText().toString());

                        if (value.name.equals(edt_name.getText().toString()) && value.email.equals(edt_email1.getText().toString())) {
                            Toast.makeText(IdPwActivity.this, value.id, Toast.LENGTH_SHORT).show();
                            Log.d("AAA", "되찌롱~~!!!!!!!!");
                        }


                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

class Info {
    int check_owner;
    String email;
    String id;
    String name;
    String password;

    public void setCheck_owner(int check_owner) {
        this.check_owner = check_owner;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCheck_owner() {
        return check_owner;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


}