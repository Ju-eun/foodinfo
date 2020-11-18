package com.project.foodinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StructclassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

class MemInfo {
    String id;
    String name;
    String email;
    String password;
    int check_owner;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getCheck_owner() {
        return check_owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCheck_owner(int check_owner) {
        this.check_owner = check_owner;
    }
}