package com.project.foodinfo.Sign;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.foodinfo.MemInfo;
import com.project.foodinfo.R;
import com.project.foodinfo.Sign.MenuAdapter;
import com.project.foodinfo.Sign.SignFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignActivity extends AppCompatActivity {

    String[] names = {"@naver.com", "@kakao.com", "@hanmail.net", "@gmail.com"};
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
    EditText et_name;

    EditText et_pw;
    EditText et_pwcheck;
    EditText et_email;
    CheckBox cb_oper;
    /*    Button btn_idcheck;*/
    Button btn_signup;
    Button btn_Rerurn;
    TextView b_tv;

    EditText f_pn;
    EditText m_pn;
    EditText e_pn;

    ImageView imageview;
    View fragment_view;
    SignFragment signFragment;
    TextView datePicker;

    MenuAdapter select_ma_uri;

    private boolean validate = false; //아이디 중복체크용용

    private final int GET_GALLERY_IMAGE = 200;

    Uri selectedImageUri;

    int input_pos = 0;


    private StorageReference mStorageRef;
    private FirebaseStorage storage;
    DatabaseReference Ref;


    String select_spinner = "";
    MemInfo minfo = new MemInfo();
    int pos;


    String key;
    MemInfo value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        LayoutInflater inflater = this.getLayoutInflater();
        fragment_view = inflater.inflate(R.layout.fragment_sign, null);

        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pw = (EditText) findViewById(R.id.et_pw);
        et_pwcheck = (EditText) findViewById(R.id.et_pwcheck);
        cb_oper = (CheckBox) findViewById(R.id.sign_frag_cb_oper);

        /*        btn_idcheck = (Button) findViewById(R.id.btn_idcheck);*/
        btn_signup = (Button) findViewById(R.id.btn_signUp);
        datePicker = (TextView) findViewById(R.id.sign_tv_yy_mm_dd);

        b_tv = (TextView) findViewById(R.id.sign_tv_yy_mm_dd);
        f_pn = (EditText) findViewById(R.id.et_phone1);
        m_pn = (EditText) findViewById(R.id.et_phone2);
        e_pn = (EditText) findViewById(R.id.et_phone3);
        btn_signup.setOnClickListener(sign_mlistener);


       /* sign_frag_btn_stcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.sign_frag_et_storename) {//id 중복체크 버튼이 눌린 경우
                    Log.d("AA", "key : " + key);
                    Ref = FirebaseDatabase.getInstance().getReference("moble-foodtruck").child("MemInfo");
                    Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("AA", "key : " + key);

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                try {
                                    key = snapshot.getKey();
                                    value = snapshot.getValue(MemInfo.class);
                                    Log.d("AA", "key : " + key);

                                    if ((sign_frag_et_storename.getText().toString().equals(value.getEmail()))){
                                        Log.d("AA", "체크한번");
                                        Toast.makeText(context, "중복된 가게이름이 있습니다.", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else{
                                        Log.d("AA", "체크한번밑");
                                        Toast.makeText(context, "사용할 수 있는 가게이름입니다.", Toast.LENGTH_SHORT).show();
                                        validate = true; //검증 완료
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }*//*
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

*/
        /*       btn_idcheck.setOnClickListener(sign_mlistener);*/

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();


                DatePickerDialog datePickerDialog = new DatePickerDialog(SignActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        try {
                            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_1);
                            Date de = format.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            String de_string = format.format(de);
                            datePicker.setText(de_string);
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                datePickerDialog.show();
            }
        });


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, names
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "선택된 아이템 :" + names[position], Toast.LENGTH_SHORT).show();
                select_spinner = names[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView123);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_DOWN);
            }
        });

        cb_oper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                signFragment = new SignFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                if (isChecked) {
                    transaction.add(R.id.frame, signFragment);
                    transaction.addToBackStack("signFragment");
                    transaction.commit();
                } else {
                    fm.popBackStack("signFragment", fm.POP_BACK_STACK_INCLUSIVE);
                    transaction.remove(signFragment);
                    transaction.commit();
                }

            }
        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i("aa", "onSaveInstanceState");
        final EditText textBox1 = (EditText)findViewById(R.id.et_name);
        CharSequence userText1 = textBox1.getText();
        outState.putCharSequence("savedText", userText1);

        final EditText textBox2 = (EditText)findViewById(R.id.et_email);
        CharSequence userText2 = textBox2.getText();
        outState.putCharSequence("savedText", userText2);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("bb", "onRe");

        final EditText textbox1 = (EditText)findViewById(R.id.et_name);
        CharSequence userText1 = savedInstanceState.getCharSequence("savedText");
        textbox1.setText(userText1);
    }

    View.OnClickListener sign_mlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); //파이어베이스에 접근
            String name = et_name.getText().toString().trim();
            String b_day = b_tv.getText().toString().trim();
            String password = et_pw.getText().toString().trim();
            String email = et_email.getText().toString().trim();

            if(cb_oper.isChecked()&&signFragment.sign==false){
                Toast.makeText(SignActivity.this, "가게 이름의" +
                        "중복체크 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
            }



            if (v.getId() == R.id.btn_signUp) {
                signFragment = (SignFragment) getSupportFragmentManager().findFragmentById(R.id.frame);

                if (name.isEmpty() || password.isEmpty() || email.isEmpty()) { //id ,name ,password, email 공백 체크
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignActivity.this);

                    builder.setTitle("값이 없어").setMessage("용용 죽겠지.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (isValidEmail(email)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignActivity.this);

                    builder.setTitle("이게 이메일이라고 만든거니?").setMessage("용용 죽겠지.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (isValidPasswd(password)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignActivity.this);

                    builder.setTitle("비밀번호 재대로 입력하세요!").setMessage("용용 죽겠지.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (et_pw.getText().toString().equals(et_pwcheck.getText().toString())) { // 비밀번호 체크

                    minfo.setName(name);
                    minfo.setPassword(password);
                    minfo.setEmail(email + select_spinner);// 스피너 값도 가져와야함
                    minfo.setBirth(b_day);
                    minfo.setphonenumber(f_pn.getText().toString(), m_pn.getText().toString(), e_pn.getText().toString());

                    if (cb_oper.isChecked()) {
                        minfo.setCheck_owner(1);
                        signFragment.onTest();
                    } else {
                        minfo.setCheck_owner(0);
                    }
                    if (minfo.blank_Check().equals("")) { //나중에 체크
                        Log.d("why?", minfo.getId() + "\n" + minfo.getName() + "\n" + minfo.getEmail() + "\n" + minfo.getPassword());
                        Toast.makeText(SignActivity.this, minfo.blank_Check() + " 값이 비어있습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        firebaseAuth.createUserWithEmailAndPassword(minfo.getEmail(), minfo.getPassword()).addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final String uid = task.getResult().getUser().getUid();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("moble-foodtruck").child("MemInfo").child(uid);//토큰 가져와서 넣고

                                    if (minfo.getCheck_owner() == 1) {
                                        storage = FirebaseStorage.getInstance();
                                        mStorageRef = storage.getReferenceFromUrl("gs://moble-foodtruck.appspot.com/oper_regis");
                                        UploadTask[] uploadTask = new UploadTask[minfo.getStore_info().getStore_Size()];

                                        Uri file[] = new Uri[minfo.getStore_info().getStore_Size()];
                                        for (int i = 0; i < minfo.getStore_info().getStore_Size(); i++) {
                                            file[i] = Uri.fromFile(new File(getPath(Uri.parse(minfo.getStore_info().getStore_menus().get(i).getMenu_img()))));

                                            StorageReference riversRef = mStorageRef.child("images/" + file[i].getLastPathSegment());
                                            Log.i("qsc1", Uri.parse(minfo.getStore_info().getStore_menus().get(i).getMenu_img()) + "");
                                            uploadTask[i] = riversRef.putFile(file[i]);

                                        }

                                        for (int i = 0; i < minfo.getStore_info().getStore_Size(); i++) {
                                            uploadTask[i].addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    // Handle unsuccessful uploads
                                                }
                                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                                    while (!uri.isComplete()) ;
                                                    Uri url = uri.getResult();
                                                    String str_str = String.valueOf(url);
                                                    Log.i("qsc2", input_pos + "");
                                                    Log.i("qsc3", str_str);
                                                    minfo.getStore_info().getStore_menus().get(input_pos).setMenu_img(str_str);
                                                    myRef.child("store_info").child("store_menus").child(input_pos + "").child("menu_img").setValue(str_str);

                                                    input_pos++;
                                                }
                                            });
                                        }
                                    }
                                    myRef.setValue(minfo);
                                    Toast.makeText(SignActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {

                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(SignActivity.this, "꺼졍", Toast.LENGTH_SHORT).show();
                }
            } /*else if (v.getId() == R.id.btn_idcheck) {//id 중복체크 버튼이 눌린 경우
                Ref = FirebaseDatabase.getInstance().getReference("moble-foodtruck").child("MemInfo");
                Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            try {
                                key = snapshot.getKey();
                                value = snapshot.getValue(MemInfo.class);
                                Log.d("AA", "key : " + key);*/

                           /*
                                if ((et_email+select_spinner.getB.toString().equals(value.getEmail()))){
                                    Log.d("AA", "체크한번");
                                    Toast.makeText(SignActivity.this, "중복된 이메일이 있습니다.", Toast.LENGTH_SHORT).show();
                                    break;
                                } else if((et_email.getText().toString().equals(value.getEmail())){
                                    Log.d("AA", "체크한번밑");
                                    Toast.makeText(SignActivity.this, "사용할 수 있는 이메일입니다.", Toast.LENGTH_SHORT).show();
                                    validate = true; //검증 완료
                                }*/
                        /*    } catch (ClassCastException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }*/
        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("뒤로가기").setMessage("저장된 내용이 삭제됩니다.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean isValidPasswd(String target) { //비밀번호 형식 검사
        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");

        Matcher m = p.matcher(target);
        if (m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidEmail(String target) {// 이메일 형식 검사
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public void getValue(MemInfo.Store_Info store_info) {

        minfo.setStore_info(store_info);
//        minfo.setStore_time(store_info.getStore_time());
//        minfo.setStore_category(store_info.getStore_category());
//        minfo.setStore_memo(store_info.getStore_memo());
    }

    protected void get_Menu_Image() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        // 권한
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            super.onActivityResult(requestCode, resultCode, data);
            selectedImageUri = data.getData();
            Log.d("asd1", selectedImageUri + "");
            imageview.setImageURI(selectedImageUri);
            select_ma_uri.myItems.get(pos).setMenuImg(selectedImageUri);


            //Log.d("asd1", imageButton.+"");

        }
    }


    public void setImageView(ImageView imageview, int pos) {
        this.imageview = imageview;
        this.pos = pos;
    }

    public void setImageUri(MenuAdapter menuAdapter) {
        this.select_ma_uri = menuAdapter;
    }

    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);


    }



}
