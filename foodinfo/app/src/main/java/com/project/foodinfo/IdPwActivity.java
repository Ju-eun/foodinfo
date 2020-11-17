package com.project.foodinfo;

import android.app.*;
import android.os.*;
import android.widget.*;


@SuppressWarnings("deprecation")

public class IdPwActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idpw);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecID =
                tabHost.newTabSpec("ID") .setIndicator("아이디 찾기");

        TabHost.TabSpec tabSpecPW =
                tabHost.newTabSpec("PW").setIndicator("비밀번호 찾기");



        tabSpecID.setContent(R.id.아이디찾기);
        tabSpecPW.setContent(R.id.비밀번호찾기);

        tabHost.addTab(tabSpecID);
        tabHost.addTab(tabSpecPW);

        tabHost.setCurrentTab(0);
    }

}