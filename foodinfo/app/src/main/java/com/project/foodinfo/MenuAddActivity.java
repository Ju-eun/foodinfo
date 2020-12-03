package com.project.foodinfo;


import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class MenuAddActivity{
    private Context context;
    private int pos;
    MemInfo.Store_Info store_info;

    ImageView menu_add;

    private Uri selectedImageUri;
    private MyAdapter myAdapter;

    private StorageReference mStorageRef;
    private FirebaseStorage storage;


    public MenuAddActivity(Context context, int pos, MyAdapter myAdapter) {
        this.myAdapter = myAdapter;
        this.context = context;
        this.pos = pos;
    }

    public void menuAddCallFunction() {

        final Dialog dlg = new Dialog(context);
        dlg.setContentView(R.layout.menu_add_mypage);
//
        dlg.show();

        menu_add = (ImageView) dlg.findViewById(R.id.menu_add_iv_menu);
        EditText menuName_add = (EditText) dlg.findViewById(R.id.menu_add_et_menuName);
        EditText menuPrice_add = (EditText) dlg.findViewById(R.id.menu_add_et_menuprice);
        Button menu_addOkbtn = (Button) dlg.findViewById(R.id.menu_addOKbtn);
        Button menu_addCancelbtn = (Button) dlg.findViewById(R.id.menu_addCancelbtn);
        ImageButton menu_add_iv_menu = (ImageButton) dlg.findViewById(R.id.menu_add_iv_menu);
        Log.i("dsa","0.1" );
        //로그인 상태에 따라서

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = user.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("moble-foodtruck").child("MemInfo").child(Uid).child("store_info");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                store_info = snapshot.getValue(MemInfo.Store_Info.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        menu_add_iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StoreinfoActivity)context).get_Menu_Image(menu_add);
            }
        });

        //최종 변경버튼
        menu_addOkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이름 가격 변경
                //기존이미지 삭제필요
                //바뀐 이미지 토큰 넣어줘야한다
                storage = FirebaseStorage.getInstance();
                mStorageRef = storage.getReferenceFromUrl("gs://moble-foodtruck.appspot.com/oper_regis");

                Uri file = Uri.fromFile(new File(getPath(selectedImageUri)));
//        Log.d("zxc", "이미지 잘 떳나요?22222 : " + selectedImageUri);

                StorageReference riversRef = mStorageRef.child("images/"+file.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("faile", "이미지 업로드 실패함");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete()) ;
                        Uri url = uri.getResult();
                        Log.d("zxc", " 바뀐 후  : " + url);

                        String str_str = String.valueOf(url);

                        MemInfo.Store_Info.Store_Menu store_menu = new MemInfo.Store_Info.Store_Menu();
                        store_menu.setMenu_img(str_str);
                        store_menu.setMenu_name(menuName_add.getText().toString());
                        store_menu.setMenu_price(menuPrice_add.getText().toString());
                        store_info.addStore_menus(store_menu);
                        myRef.setValue(store_info);
                        myAdapter.notifyDataSetChanged();
                        dlg.dismiss();

                    }
                });
            }
        });

        //변경 취소 버튼
        menu_addCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //취소 되면 이미지 되돌림


                dlg.dismiss();
            }
        });
    }

    public void getNewPass(Uri selectedImageUri){
        this.selectedImageUri = selectedImageUri;
        Log.d("zxc", "이미지 잘 떳나요? : " + selectedImageUri);
    }

    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(context, uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

}
