package com.project.foodinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

class activity_MyReview extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<String> al = new ArrayList<String>();
    ArrayAdapter aa;
    String sample[] = {"a","a","a","a","a","a","a","a","a","a","a","a"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();  //제목줄 객체 얻어오기
        actionBar.setTitle("리뷰관리");  //액션바 제목설정
        actionBar.setDisplayHomeAsUpEnabled(true);   //업버튼 <- 만들기


        ListView lv = (ListView) findViewById(R.id.lv_review);
        aa = new ArrayAdapter<String>(this, R.layout.myreview_row, R.id.tv_name,sample );

        lv.setAdapter(aa);
        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

