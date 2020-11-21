package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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
    Button modifybtn, Exit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ed_password = findViewById(R.id.ed_pw);
        ed_id = findViewById(R.id.ed_id);
        modifybtn = findViewById(R.id.modify_btn);
        Exit_btn = findViewById(R.id.Exit_btn);
        ed_email = findViewById(R.id.ed_email);
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
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MemInfo m = snapshot.getValue(MemInfo.class);

                        if (m.getId().equals(ed_id.getText().toString()) && m.getPassword().equals(ed_password.getText().toString()))
                        {
                            Toast.makeText(MypageActivity.this, "비밀번호 일치", Toast.LENGTH_SHORT).show();
                            Passworddialog passworddialog = new Passworddialog(MypageActivity.this);
                            // 커스텀 다이얼로그를 호출한다.
                            passworddialog.callFunction();
                        }
                        else{
                            Toast.makeText(MypageActivity.this, "비밀번호를 정확하게 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        Exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MemInfo m = snapshot.getValue(MemInfo.class);
                        if (m.getId().equals(ed_id.getText().toString()) && m.getPassword().equals(ed_password.getText().toString()))
                        {
                            Toast.makeText(MypageActivity.this, "비밀번호 일치", Toast.LENGTH_SHORT).show();
                            Exitdialog exitdialog = new Exitdialog(MypageActivity.this);
                            // 커스텀 다이얼로그를 호출한다.
                            exitdialog.callFunction();
                        }
                        else{
                            Toast.makeText(MypageActivity.this, "비밀번호를 정확하게 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    class Exitdialog{

        private Context context;

        public Exitdialog(Context context)
        {
            this.context = context;
        }

        public void callFunction() {

            // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
            final Dialog dlg = new Dialog(context);
            // 커스텀 다이얼로그의 레이아웃을 설정한다.
            dlg.setContentView(R.layout.activity_mypagedialog_exit);
            // 커스텀 다이얼로그를 노출한다.
            dlg.show();

            // 커스텀 다이얼로그의 각 위젯들을 정의한다.
            EditText pwchange = (EditText) dlg.findViewById(R.id.pwchange);
            EditText pwchange2 = (EditText) dlg.findViewById(R.id.pwchange2);
            Button pwchangebtn = (Button)dlg.findViewById(R.id.pwchangebtn);
            Button cancelbtn = (Button)dlg.findViewById(R.id.cancelbtn);

            pwchangebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(pwchange.getText().toString().equals(ed_password.getText().toString()) && pwchange.getText().toString().equals(pwchange2.getText().toString()))
                    {
                        myRef.removeValue();
                        Toast.makeText(context, "회원탈퇴 완료", Toast.LENGTH_SHORT).show();
                        dlg.dismiss();
                    }
                    else{
                        Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dlg.dismiss();
                }
            });
        }
    }

    class Passworddialog{

        private Context context;

        public Passworddialog(Context context)
        {
            this.context = context;
        }

        public void callFunction() {

            // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
            final Dialog dlg = new Dialog(context);
            // 커스텀 다이얼로그의 레이아웃을 설정한다.
            dlg.setContentView(R.layout.activity_mypagedialog_password);
            // 커스텀 다이얼로그를 노출한다.
            dlg.show();

            // 커스텀 다이얼로그의 각 위젯들을 정의한다.
            EditText pwchange = (EditText) dlg.findViewById(R.id.pwchange);
            EditText pwchange2 = (EditText) dlg.findViewById(R.id.pwchange2);
            Button pwchangebtn = (Button)dlg.findViewById(R.id.pwchangebtn);
            Button cancelbtn = (Button)dlg.findViewById(R.id.cancelbtn);
            pwchangebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(pwchange.getText().toString().equals(pwchange2.getText().toString()))
                    {
                        memInfo.setEmail(ed_email.getText().toString());
                        memInfo.setPassword(pwchange.getText().toString());
                        memInfo.setId(ed_id.getText().toString());
                        memInfo.setName(ed_name.getText().toString());
                        memInfo.setCheck_owner(1);
                        myRef.setValue(memInfo);

                        if(user.isChecked())
                            memInfo.setCheck_owner(0);
                        else
                            memInfo.setCheck_owner(1);
                        Toast.makeText(context, "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();
                        dlg.dismiss();
                    }

                    else{
                        Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dlg.dismiss();
                }
            });
        }
    }
}

