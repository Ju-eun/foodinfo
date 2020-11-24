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
    EditText edt_idpw_name, edt_idpw_id, edt_idpw_email1, edt_idpw_email2;
    DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idpw);

        edt_idpw_name = findViewById(R.id.edt_idpw_name);
        edt_idpw_id = findViewById(R.id.edt_idpw_id);
        edt_idpw_email1 = findViewById(R.id.edt_idpw_email1);
        edt_idpw_email2 = findViewById(R.id.edt_idpw_email2);

        btn_idpw_1 = ((Button) findViewById(R.id.btn_idpw_1));
        btn_idpw_1.setOnClickListener(this);
        btn_idpw_2 = ((Button) findViewById(R.id.btn_idpw_1));
        btn_idpw_2.setOnClickListener(this);

        TabHost tabHost = getTabHost();                                          //탭호스트 호출 가장먼저 사용

        TabHost.TabSpec tabSpecID =                                              // "Tab Spec" tag를 가진 TabSpec 객체 생성.   ID
                tabHost.newTabSpec("ID").setIndicator("아이디 찾기");       // tag의 이름을 아이디 찾기로 정한다.

        TabHost.TabSpec tabSpecPW =
                tabHost.newTabSpec("PW").setIndicator("비밀번호 찾기");

        tabSpecID.setContent(R.id.아이디찾기);                                  // 탭이 눌려졌을 때 FrameLayout에 표시될 Content 뷰에 대한 리소스 id 지정.
        tabSpecPW.setContent(R.id.패스워드찾기);                                // 탭이 눌려졌을 때 FrameLayout에 표시될 Content 뷰에 대한 리소스 id 지정. 패스워드

        tabHost.addTab(tabSpecID);                                              // TabHost에 탭 추가.
        tabHost.addTab(tabSpecPW);                                              // TabHost에 탭 추가.

        tabHost.setCurrentTab(0);                                               //초기 탭 설정


    }

    @Override
    public void onClick(View view) {

        Ref = FirebaseDatabase.getInstance().getReference("moble-foodtruck").child("MemInfo");                  //모블 푸드트럭에 있는 MenInfo에 들어있는 정보를 가지고온다.
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {                                                 //데이터 불러오기
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Boolean b = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {                                          //데이터스냅샷의 데이터를 가지고옴
                    try {                                                                                           //에러가 발생할 수 있는 코드

                        key = snapshot.getKey();                                                                    //키를 얻음
                        value = snapshot.getValue(Info.class);                                                      //인포 클래스 객체 안에 있는 값 불러오기

                        Log.d("AA", "key : " + key);
                        Log.d("AA", value.name);
                        Log.d("AA", value.id);
                        Log.d("AA", value.email);
                        Log.d("AA", value.password);
                        Log.d("AA", edt_idpw_name.getText().toString());
                        Log.d("AA", edt_idpw_email1.getText().toString());
                        Log.d("AA", edt_idpw_email2.getText().toString());

                        if (value.name.equals(edt_idpw_name.getText().toString()) && value.email.equals(edt_idpw_email1.getText().toString())) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);                   //디스는 아이디패스워드 액티비티, 알람창 띄우기
                            // 여기서 부터는 알림창의 속성 설정
                            builder.setTitle("사용자의 아이디").setMessage("아이디는 " + value.id + " 입니다.");                 // 제목 설정, 메시지 설정
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {                       //확인 버튼 클릭시 창 닫기
                                @Override
                    public void onClick(DialogInterface dialog, int id) {                                           //토스크 메시지 알람 띄어주기(하고싶은 사람만)
                    }
                });
                AlertDialog alertDialog = builder.create();                                                         //알람창 객체 생성
                alertDialog.show();                                                                                 //알람창 보여주기
                b = true;
                break;
            }

        } catch (ClassCastException e) {
                        e.printStackTrace();                                                                                    //오류 출력
                    }
                }
                if (b == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                    builder.setTitle("오류").setMessage("입력한 정보의 아이디가 없습니다.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
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

                Boolean c = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {

                        key = snapshot.getKey();
                        value = snapshot.getValue(Info.class);

                        Log.d("AA", "key : " + key);
                        Log.d("AA", value.name);
                        Log.d("AA", value.email);
                        Log.d("AA", edt_idpw_name.getText().toString());
                        Log.d("AA", edt_idpw_email1.getText().toString());

                        if (value.id.equals(edt_idpw_id.getText().toString()) && value.email.equals(edt_idpw_email2.getText().toString())) {
                            FirebaseAuth auth = FirebaseAuth.getInstance();

                            AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                            builder.setTitle("사용자의 패스워드").setMessage("패스워드를 이메일에 보냈습니다.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            String emailAdress = value.email;
                            auth.sendPasswordResetEmail(emailAdress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }

                            });
                            c = true;
                            break;
                        }

                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
                if (c == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                    builder.setTitle("오류").setMessage("입력한 정보의 아이디가 없습니다.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
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