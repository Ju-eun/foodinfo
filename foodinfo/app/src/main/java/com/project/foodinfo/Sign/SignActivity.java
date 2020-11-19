package com.project.foodinfo.Sign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.foodinfo.R;

public class SignActivity extends AppCompatActivity {

    String[] names = {"@naver.com", "@kakao.com", "@hanmail.net", "@gmail.com"};

    EditText et_name;
    EditText et_id;
    EditText et_pw;
    EditText et_pwcheck;
    EditText et_email;
    CheckBox cb_oper;

    Button btn_idcheck;
    Button btn_signup;
    View.OnClickListener mlistener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        Button btn_oper_picture = (Button) findViewById(R.id.btn_oper_picture);
        et_id = (EditText) findViewById(R.id.id);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pw = (EditText) findViewById(R.id.et_pw);
        et_pwcheck = (EditText) findViewById(R.id.et_pwcheck);
        cb_oper = (CheckBox) findViewById(R.id.sign_frag_cb_oper);

        btn_idcheck = (Button) findViewById(R.id.btn_idcheck);
        btn_signup = (Button) findViewById(R.id.btn_signUp);


        btn_signup.setOnClickListener(mlistener);
        btn_idcheck.setOnClickListener(mlistener);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, names
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"선택된 아이템 :" +names[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView123);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_DOWN);
            }
        });

        cb_oper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SignFragment signFragment = new SignFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();


                if(isChecked){
                   transaction.add(R.id.frame, signFragment);
                   transaction.addToBackStack("signFragment");
                   transaction.commit();
                }
                else {
                    fm.popBackStack("signFragment", fm.POP_BACK_STACK_INCLUSIVE);
                    transaction.remove(signFragment);
                    transaction.commit();
                }

            }
        });




        /*btn_oper_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignActivity.this, oper.class);

                startActivity(intent);
            }
        });

    }

    View.OnClickListener mlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_signUp){

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("moble-foodtruck").child("MemInfo").child(et_id.getText().toString());
                if(et_pw.getText().toString().equals(et_pwcheck.getText().toString())){

                    MemInfo minfo = new MemInfo();
                    minfo.setId(et_id.getText().toString());
                    minfo.setName(et_name.getText().toString());
                    minfo.setPassword(et_pw.getText().toString());
                    minfo.setEmail(et_email.getText().toString());
                    if(cb_oper.isChecked()){
                        minfo.setCheck_owner(1);
                    }
                    else{
                        minfo.setCheck_owner(0);
                    }

                    myRef.setValue(minfo);

                }
                else{
                    Toast.makeText(SignActivity.this, "꺼졍", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
*/

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("뒤로가기").setMessage("저장된 내용이 삭제됩니다.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}


