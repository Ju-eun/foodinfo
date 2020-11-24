//package com.project.foodinfo.Sign;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ScrollView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.project.foodinfo.MemInfo;
//import com.project.foodinfo.R;
//
//public class SignActivity extends AppCompatActivity {
//
//    String[] names = {"@naver.com", "@kakao.com", "@hanmail.net", "@gmail.com"};
//    String store_name = null;
//    String store_opentime = null;
//    String store_closetime = null;
//    String store_memo = null;
//    String store_category = null;
//
//    EditText et_name;
//    EditText et_id;
//    EditText et_pw;
//    EditText et_pwcheck;
//    EditText et_email;
//    CheckBox cb_oper;
//
//    Button btn_idcheck;
//    Button btn_signup;
//
//
//    View fragment_view;
//    SignFragment signFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        LayoutInflater inflater = this.getLayoutInflater();
//        fragment_view = inflater.inflate(R.layout.fragment_sign, null);
//
//
////        Button btn_oper_picture = (Button) findViewById(R.id.btn_oper_picture);
//        et_id = (EditText) findViewById(R.id.id);
//        et_name = (EditText) findViewById(R.id.et_name);
//        et_email = (EditText) findViewById(R.id.et_email);
//        et_pw = (EditText) findViewById(R.id.et_pw);
//        et_pwcheck = (EditText) findViewById(R.id.et_pwcheck);
//        cb_oper = (CheckBox) findViewById(R.id.sign_frag_cb_oper);
//
//        btn_idcheck = (Button) findViewById(R.id.btn_idcheck);
//        btn_signup = (Button) findViewById(R.id.btn_signUp);
//
//        btn_signup.setOnClickListener(sign_mlistener);
//        btn_idcheck.setOnClickListener(sign_mlistener);
//
//        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_spinner_item, names
//        );
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "선택된 아이템 :" + names[position], Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView123);
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.fullScroll(scrollView.FOCUS_DOWN);
//            }
//        });
//
//        cb_oper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                signFragment = new SignFragment();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//
//                if (isChecked) {
//                    transaction.add(R.id.frame, signFragment);
//                    transaction.addToBackStack("signFragment");
//                    transaction.commit();
//                } else {
//                    fm.popBackStack("signFragment", fm.POP_BACK_STACK_INCLUSIVE);
//                    transaction.remove(signFragment);
//                    transaction.commit();
//                }
//
//            }
//        });
//
//
//    }
//
//    View.OnClickListener sign_mlistener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); //파이어베이스에 접근
//            if (v.getId() == R.id.btn_signUp) {
//                if (et_pw.getText().toString().equals(et_pwcheck.getText().toString())) { // 비밀번호 체크
//                    MemInfo minfo = new MemInfo();
//
//                    minfo.setId(et_id.getText().toString());
//                    minfo.setName(et_name.getText().toString());
//                    minfo.setPassword(et_pw.getText().toString());
//                    minfo.setEmail(et_email.getText().toString());// 스피너 값도 가져와야함
//
//                    //프레그먼트 값 가져오기
////                    SignFragment sf = (SignFragment) getSupportFragmentManager().findFragmentById(R.id.frame);
////                    sf.getFragmentValue();
//
//                    if (cb_oper.isChecked()) {
//                        minfo.setCheck_owner(1);
//                        minfo.setStore_name(store_name);
//                        minfo.setStore_category(store_category);
//                        minfo.setStore_time(store_opentime + store_closetime);
//                        minfo.setStore_memo(store_memo);
//                    } else {
//                        minfo.setCheck_owner(0);
//                        minfo.setStore_name(store_name);
//                        minfo.setStore_category(store_category);
//                        minfo.setStore_time(store_opentime + store_closetime);
//                        minfo.setStore_memo(store_memo);
//                    }
//                    if (minfo.blank_Check() == null) {
//                        Log.d("why?", minfo.getId()+"\n" +minfo.getName() +"\n" +minfo.getEmail()+"\n"+minfo.getPassword());
//                        Toast.makeText(SignActivity.this, minfo.blank_Check() + " 값이 비어있습니다.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        firebaseAuth.createUserWithEmailAndPassword("water8048@naver.com", minfo.getPassword()).addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                Toast.makeText(SignActivity.this, "?????", Toast.LENGTH_SHORT).show();
//                                if(!task.isSuccessful()){
//                                    final String uid = task.getResult().getUser().getUid();
//                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                    DatabaseReference myRef = database.getReference("moble-foodtruck").child("MemInfo").child(uid); //토큰 가져와서 넣고
//                                    myRef.setValue(minfo);
//
//                                    Toast.makeText(SignActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        });
//                    }
//                } else {
//                    Toast.makeText(SignActivity.this, "꺼졍", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }
//    };
//
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("뒤로가기").setMessage("저장된 내용이 삭제됩니다.");
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                finish();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//
//            }
//        });
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//
//    public void setFragmentValue(String store_name, String store_opentime, String store_closetime, String store_memo, String store_category) {
//        this.store_name = store_name;
//        this.store_category = store_category;
//        this.store_opentime = store_opentime;
//        this.store_closetime = store_closetime;
//        this.store_memo = store_memo;
//    }
//}
//
//
