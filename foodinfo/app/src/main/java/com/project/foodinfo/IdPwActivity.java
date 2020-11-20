package com.project.foodinfo;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
<<<<<<< HEAD
=======

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

<<<<<<< HEAD
=======
public class IdPwActivity extends TabActivity implements View.OnClickListener {
    String key;
    Info value;
    Button btn_1;
    EditText edt_name, edt_email1;
    DatabaseReference Ref;
>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93

@SuppressWarnings("deprecation")

public class IdPwActivity extends TabActivity implements View.OnClickListener, OnCompleteListener<Void> {
    String key;
    Info value;
    Button btn_idpw_1, btn_idpw_2;
    EditText edt_idpw_name, edt_idpw_id, edt_idpw_email1, edt_idpw_email2;
    DatabaseReference Ref;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idpw);

<<<<<<< HEAD
        edt_idpw_name = findViewById(R.id.edt_idpw_name);
        edt_idpw_id = findViewById(R.id.edt_idpw_id);
        edt_idpw_email1 = findViewById(R.id.edt_idpw_email1);
        edt_idpw_email2 = findViewById(R.id.edt_idpw_email2);

        btn_idpw_1 = ((Button) findViewById(R.id.btn_idpw_1));
        btn_idpw_1.setOnClickListener(this);
        btn_idpw_2 = ((Button) findViewById(R.id.btn_idpw_1));
        btn_idpw_2.setOnClickListener(this);

=======
        edt_name = findViewById(R.id.edt_name);
        edt_email1 = findViewById(R.id.edt_email1);

        btn_1 = ((Button) findViewById(R.id.btn_1));
        btn_1.setOnClickListener(this);
>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93
        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecID =
                tabHost.newTabSpec("ID").setIndicator("아이디 찾기");

        TabHost.TabSpec tabSpecPW =
                tabHost.newTabSpec("PW").setIndicator("비밀번호 찾기");

        tabSpecID.setContent(R.id.아이디찾기);
        tabSpecPW.setContent(R.id.패스워드찾기);

        tabHost.addTab(tabSpecID);
        tabHost.addTab(tabSpecPW);

        tabHost.setCurrentTab(0);
<<<<<<< HEAD
        firebaseAuth = FirebaseAuth.getInstance();
=======

>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93

    }

    @Override
<<<<<<< HEAD
    public void onClick(View view) {
=======
    public void onClick(View v) {
>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93

        Ref = FirebaseDatabase.getInstance().getReference("moble-foodtruck").child("MemInfo");
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
<<<<<<< HEAD
                boolean b = false;
=======

>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {

                        key = snapshot.getKey();
                        value = snapshot.getValue(Info.class);

<<<<<<< HEAD

                        Log.d("AA", "key : " + key);
                        Log.d("AA", value.name);
                        Log.d("AA", value.email);
                        Log.d("AA", edt_idpw_name.getText().toString());
                        Log.d("AA", edt_idpw_email1.getText().toString());

                        if (value.name.equals(edt_idpw_name.getText().toString()) && value.email.equals(edt_idpw_email1.getText().toString()))  {
                            AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                            builder.setTitle("아이디").setMessage("사용자의 아이디는: "+value.id+" 입니다.");
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
                 if(b == false) {
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
                boolean b = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {

                        key = snapshot.getKey();
                        value = snapshot.getValue(Info.class);

                        Log.d("AA", "key : " + key);
                        Log.d("AA", value.name);
                        Log.d("AA", value.email);
                        Log.d("AA", edt_idpw_name.getText().toString());
                        Log.d("AA", edt_idpw_email1.getText().toString());

                        if (value.id.equals(edt_idpw_id.getText().toString()) && value.email.equals(edt_idpw_email2.getText().toString()))  {
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            String emailAdress = value.email;
                           auth.sendPasswordResetEmail(emailAdress).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   /*if*/
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
                                   else {
                                       AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                                       builder.setTitle("오류").setMessage("입력한 정보의 패스워드가 없습니다.");
                                       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int password) {
                                           }
                                       });
                                       AlertDialog alertDialog = builder.create();
                                       alertDialog.show();

                                   }
                               }
                           });


                           /* b = true;
                            break;*/
                        }

                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
                /* if(b == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IdPwActivity.this);
                    builder.setTitle("오류").setMessage("입력한 정보의 패스워드가 없습니다.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int password) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }*/
=======
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


>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
<<<<<<< HEAD
            }
        });
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {

    }
=======

            }
        });
    }
>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93
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
<<<<<<< HEAD
    }

    public int getCheck_owner() {
        return check_owner;
    }

    public String getEmail() {
        return email;
    }

=======
    }

    public int getCheck_owner() {
        return check_owner;
    }

    public String getEmail() {
        return email;
    }

>>>>>>> 3fafbedf64d985216590b26f3aa0484d71f1dd93
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