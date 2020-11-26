package com.project.foodinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.foodinfo.Sign.SignActivity;

//import com.project.foodinfo.Sign.SignActivity;

public class LoginActivity extends AppCompatActivity {
    EditText et_id;
    EditText et_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);

    }

    //로그인 버튼 클릭시 이벤트
    public void loginOnClick(View v){

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