package com.project.foodinfo.Sign;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.project.foodinfo.R;


public class OperActivity extends AppCompatActivity {

   private final int GET_GALLERY_IMAGE = 200;
   public ImageView imageview;
   Button btn_oper_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oper);
        imageview = findViewById(R.id.iv_oper);
        btn_oper_image = (Button)findViewById(R.id.btn_oper_image);
        btn_oper_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        , "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data!=null && data.getData() != null)
        {
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}