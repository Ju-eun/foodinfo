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
    MemInfo value;
    Button btn_idpw_1, btn_idpw_2;
    EditText edt_idpw_name, edt_idpw_birth, edt_idpw_email, edt_idpw_number;
    DatabaseReference Ref;
    MemInfo memInfo;

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
                        memInfo = snapshot.getValue(MemInfo.class);
                        Log.d("AA1", "key : " + key);
                        Log.d("AA2", memInfo.getEmail());
                        Log.d("AA3", edt_idpw_name.getText().toString());
                        Log.d("AA4", edt_idpw_number.getText().toString());

                        if (memInfo.getName().equals(edt_idpw_name.getText().toString()) && memInfo.getphonenumber().equals(edt_idpw_number.getText().toString())) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                                    builder.setTitle("이메일").setMessage("이메일은" +memInfo.getEmail()+ " 입니다.");
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
                        memInfo = snapshot.getValue(MemInfo.class);
                        Log.d("AA", "key : " + key);
                        Log.d("AA", memInfo.getEmail());
                        Log.d("AA", edt_idpw_name.getText().toString());
                        Log.d("AA", edt_idpw_number.getText().toString());

                        if (memInfo.getBirth().equals(edt_idpw_birth.getText().toString()) && memInfo.getEmail().equals(edt_idpw_email.getText().toString())) {
                            Log.d("AA", memInfo.getEmail());
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            String emailAdress = memInfo.getEmail();
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
