package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.foodinfo.Sign.SignActivity;


public class LoginActivity extends AppCompatActivity {
    EditText et_email;
    EditText et_password;
    FirebaseAuth firebaseAuth;
    int abc = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_email = findViewById(R.id.et_sign_id);
        et_password = findViewById(R.id.et_sing_password);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    //로그인 버튼 클릭시 이벤트
    public void loginOnClick(View v) {

        String email = et_email.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //회원가입 버튼 클릭시 이벤트
    public void signOnClick(View v){
        Intent intent = new Intent(LoginActivity.this, SignActivity.class);
        startActivity(intent);
    }

    //아이디 비밀번호 찾기 클릭시 이벤트
    public void findOnClick(View v){
      Intent intent = new Intent(LoginActivity.this, IdPwActivity.class);
        startActivity(intent);
    }

    //핸드폰 뒤로가기 눌렸을시
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}