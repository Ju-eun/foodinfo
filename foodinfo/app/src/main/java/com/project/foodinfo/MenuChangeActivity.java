package com.project.foodinfo;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuChangeActivity{

    private Context context;

    public MenuChangeActivity(Context context) {
        this.context = context;
    }

    public void menuChangeCallFunction() {

        final Dialog dlg = new Dialog(context);
        dlg.setContentView(R.layout.menu_revice);
//
        dlg.show();

        ImageView menu_modify = (ImageView) dlg.findViewById(R.id.menu_modify_iv_menu);
        EditText menuName_modify = (EditText) dlg.findViewById(R.id.menu_modify_et_menuName);
        EditText menuPrice_modify = (EditText) dlg.findViewById(R.id.menu_modify_et_menuprice);
        Button menu_modifybtn = (Button) dlg.findViewById(R.id.menu_iv_modifybtn);
        Button menu_modifyOkbtn = (Button) dlg.findViewById(R.id.menu_modifyOKbtn);
        Button menu_modifyCancelbtn = (Button) dlg.findViewById(R.id.menu_modifyCancelbtn);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child("123");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuName_modify.setText(MemInfo.class.get);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        menu_modifyOkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        menu_modifyCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"취소했습니다.",Toast.LENGTH_SHORT).show();

                dlg.dismiss();
            }
        });


    }
}