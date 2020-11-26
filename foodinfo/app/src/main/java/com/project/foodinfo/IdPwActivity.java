package com.project.foodinfo;

import android.app.*;
import android.content.DialogInterface;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    Button btn_idpw_1, btn_idpw_2;
    EditText edt_idpw_name, edt_idpw_birth, edt_idpw_email, edt_idpw_number;
    DatabaseReference Ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idpw);

        edt_idpw_name = findViewById(R.id.edt_idpw_name);
        edt_idpw_birth = findViewById(R.id.edt_idpw_birth);
        edt_idpw_email = findViewById(R.id.edt_idpw_email);
        edt_idpw_number = findViewById(R.id.edt_idpw_number);

        btn_idpw_1 = ((Button) findViewById(R.id.btn_idpw_1));
        btn_idpw_1.setOnClickListener(this);
        btn_idpw_2 = ((Button) findViewById(R.id.btn_idpw_1));
        btn_idpw_2.setOnClickListener(this);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecEmail =
                tabHost.newTabSpec("Email").setIndicator("이메일 찾기");

        TabHost.TabSpec tabSpecPW =
                tabHost.newTabSpec("PW").setIndicator("비밀번호 찾기");

        tabSpecEmail.setContent(R.id.이메일찾기);
        tabSpecPW.setContent(R.id.패스워드찾기);

        tabHost.addTab(tabSpecEmail);
        tabHost.addTab(tabSpecPW);

        tabHost.setCurrentTab(0);


    }

    @Override
    public void onClick(View view) {

        Ref = FirebaseDatabase.getInstance().getReference("moble-foodtruck").child("MemInfo");
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean b = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    try {

                        key = snapshot.getKey();
                        value = snapshot.getValue(Info.class);
                        Log.d("AA", "key : " + key);
                        Log.d("AA", value.email);
                        Log.d("AA", edt_idpw_name.getText().toString());
                        Log.d("AA", edt_idpw_number.getText().toString());

                        if (value.name.equals(edt_idpw_name.getText().toString()) && value.number.equals(edt_idpw_number.getText().toString())) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                                    builder.setTitle("이메일").setMessage("이메일은" +value.email+ " 입니다.");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                            b = true;
                            break;
                            }


                        } catch (ClassCastException e) {
                             e.printStackTrace();
                    }

                }
                if (b == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                    builder.setTitle("오류").setMessage("이름 또는 전화번호가 잘못되었습니다.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int password) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void onClick2(View view) {

        Ref = FirebaseDatabase.getInstance().getReference("moble-foodtruck").child("MemInfo");
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean a = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    try {

                        key = snapshot.getKey();
                        value = snapshot.getValue(Info.class);
                        Log.d("AA", "key : " + key);
                        Log.d("AA", value.email);
                        Log.d("AA", edt_idpw_name.getText().toString());
                        Log.d("AA", edt_idpw_number.getText().toString());

                        if (value.birth.equals(edt_idpw_birth.getText().toString()) && value.email.equals(edt_idpw_email.getText().toString())) {
                            Log.d("AA", value.email);
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            String emailAdress = value.email;
                            auth.sendPasswordResetEmail(emailAdress).addOnCompleteListener(task ->  {
                                if (task.isSuccessful()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                                    builder.setTitle("패스워드").setMessage("사용자의 이메일에 전송했습니다.");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                }

                            });
                            a = true;
                            break;
                        }

                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
                if (a == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                    builder.setTitle("오류").setMessage("생년월일 또는 이메일이 잘못되었습니다.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int password) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
    String number;
    String birth;

    public int getCheck_owner() {
        return check_owner;
    }

    public void setCheck_owner(int check_owner) {
        this.check_owner = check_owner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}