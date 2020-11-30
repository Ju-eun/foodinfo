package com.project.foodinfo;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MenuChangeActivity{

    private Context context;
    private int pos;
    MemInfo.Store_Info store_info;

    ImageView menu_modify;

    public MenuChangeActivity(Context context, int pos) {
        this.context = context;
        this.pos = pos;
    }

    public void menuChangeCallFunction() {

        final Dialog dlg = new Dialog(context);
        dlg.setContentView(R.layout.menu_revice);
//
        dlg.show();

        menu_modify = (ImageView) dlg.findViewById(R.id.menu_modify_iv_menu);
        EditText menuName_modify = (EditText) dlg.findViewById(R.id.menu_modify_et_menuName);
        EditText menuPrice_modify = (EditText) dlg.findViewById(R.id.menu_modify_et_menuprice);
        Button menu_modifybtn = (Button) dlg.findViewById(R.id.menu_iv_modifybtn);
        Button menu_modifyOkbtn = (Button) dlg.findViewById(R.id.menu_modifyOKbtn);
        Button menu_modifyCancelbtn = (Button) dlg.findViewById(R.id.menu_modifyCancelbtn);

        //로그인 상태에 따라서
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = user.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child(Uid).child("store_info");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                store_info = snapshot.getValue(MemInfo.Store_Info.class);

                Glide.with(context).
                        load(store_info.getStore_menus().get(pos).getMenu_img()).
                        into(menu_modify);
                menuName_modify.setText(store_info.getStore_menus().get(pos).getMenu_name());
                menuPrice_modify.setText(store_info.getStore_menus().get(pos).getMenu_price());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //이미지 변경 버튼
        menu_modifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.getContext();
                ((StoreinfoActivity)context).get_Menu_Image(store_info, menu_modify , pos);
                //새로 넣은 이미지 토큰 uri;
            }
        });

        //최종 변경버튼
        menu_modifyOkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //변경 취소 버튼
        menu_modifyCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "취소했습니다.", Toast.LENGTH_SHORT).show();

                dlg.dismiss();
            }
        });
    }
}