package com.project.foodinfo;

import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.firebase.storage.StorageReference;

public class MyItem {
    private int num;
    private Uri menuImg;
    private String name;
    private String price;
    private String imagename;

    public String getImagename() {

        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public StorageReference getImg() {
        return img;
    }

    public void setImg(StorageReference img) {
        this.img = img;
    }

    private StorageReference img;

    public TextWatcher nameWatcher;
    public TextWatcher priceWatcher;


    public Uri getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(Uri menuImg) {
        this.menuImg = menuImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



    public MyItem(){
        nameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        priceWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                price = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }


}

