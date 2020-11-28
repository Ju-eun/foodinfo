package com.project.foodinfo.Sign;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.foodinfo.MemInfo;
import com.project.foodinfo.MyItem;
import com.project.foodinfo.R;
import com.project.foodinfo.MemInfo.Store_Info.Store_Menu;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SignFragment extends Fragment {
    Spinner spinner;
    Button btn_sign_menu_plus;
    Button btn_sign_menu_minus;
    Button btn_oper_picture;
    ListView lv_sign_menu;

    MemInfo.Store_Info store_info = new MemInfo.Store_Info();

    EditText sign_frag_et_storename;
    EditText sign_frag_et_categori;
    EditText sign_frag_et_firstopen;
    EditText sign_frag_et_close;
    EditText sign_frag_et_memo;

    MenuAdapter menuAdapter;
    Context context;

    String selected_item;

    String[] names = {"한식", "중식", "일식", "기타"};



    private int menu_size = 0;

    public SignFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        context = container.getContext();

        menuAdapter = new MenuAdapter(getActivity());

        spinner = view.findViewById(R.id.sign_frag_spinner_categori);
        lv_sign_menu = view.findViewById(R.id.sign_frag_lv_menulist);
        btn_sign_menu_plus = view.findViewById(R.id.sign_frag_btn_plus);
        btn_sign_menu_minus = view.findViewById(R.id.sign_frag_btn_minus);
        btn_oper_picture = view.findViewById(R.id.sign_frag_btn_picture);

        sign_frag_et_storename = view.findViewById(R.id.sign_frag_et_storename);
        sign_frag_et_categori = view.findViewById(R.id.sign_frag_et_categori);
        sign_frag_et_firstopen = view.findViewById(R.id.sign_frag_et_firstopen);
        sign_frag_et_close = view.findViewById(R.id.sign_frag_et_close);
        sign_frag_et_memo = view.findViewById(R.id.sign_frag_et_memo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item, names
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),"선택된 아이템 :" +names[position], Toast.LENGTH_SHORT).show();
                    selected_item = names[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        menuAdapter.addItem("","", menu_size);
        menu_size++;

        lv_sign_menu.setAdapter(menuAdapter);

        btn_sign_menu_plus.setOnClickListener(mListener);
        btn_sign_menu_minus.setOnClickListener(mListener);

        btn_oper_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OperActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == btn_sign_menu_plus.getId()){

                menuAdapter.addItem("","", menu_size);
                menu_size++;

                int totalHeight = 0;
                int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_sign_menu.getWidth(), View.MeasureSpec.AT_MOST);
                for (int i = 0; i < menuAdapter.getCount(); i++) {
                    View listItem = menuAdapter.getView(i, null, lv_sign_menu);
                    //listItem.measure(0, 0);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();

                    listItem.findViewById(R.id.et_menu_row_name);
                }
                ViewGroup.LayoutParams params = lv_sign_menu.getLayoutParams();
                params.height = totalHeight;

                lv_sign_menu.setLayoutParams(params);

//                for(int i  = 0; i < menuAdapter.getCount(); i++){
//                    myItem = menuAdapter.getItem(i);
//                    almy.add(myItem);
//                }
//                menuAdapter.setMyItems(almy);
                menuAdapter.notifyDataSetChanged();
            }
            else if(v.getId() == btn_sign_menu_minus.getId()){
                menuAdapter.removeItem();
                menu_size--;

                int totalHeight = 0;
                int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_sign_menu.getWidth(), View.MeasureSpec.AT_MOST);
                for (int i = 0; i < menuAdapter.getCount(); i++) {
                    View listItem = menuAdapter.getView(i, null, lv_sign_menu);
                    //listItem.measure(0, 0);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
                ViewGroup.LayoutParams params = lv_sign_menu.getLayoutParams();
                params.height = totalHeight;
                lv_sign_menu.setLayoutParams(params);

                menuAdapter.notifyDataSetChanged();
            }
        }
    };

    public void onTest(){
        ArrayList<Store_Menu> al_menu = new ArrayList<Store_Menu>();

        store_info.setStore_name(sign_frag_et_storename.getText().toString().trim());
        store_info.setStore_time(sign_frag_et_firstopen.getText().toString().trim() +" ~ " +sign_frag_et_close.getText().toString().trim());
        store_info.setStore_memo(sign_frag_et_memo.getText().toString().trim());
        store_info.setStore_name(sign_frag_et_storename.getText().toString().trim());
        if(selected_item.equals(names[3])){
            store_info.setStore_category(sign_frag_et_categori.getText().toString().trim());
        }
        else{

            store_info.setStore_category(selected_item);
        }


        for(int i = 0; i < menuAdapter.getCount(); i++){

            Store_Menu store_menu = new Store_Menu();
            
            MyItem myItem = menuAdapter.getItem(i);

            store_menu.setMenu_name(myItem.getName());//myItem.getName());
            store_menu.setMenu_price(myItem.getPrice());
            store_menu.setMenu_img(myItem.getMenuImg() + "");//이미지 넣는부분
            al_menu.add(store_menu);
        }
        //이 근처에서 이뤄줘야함
        store_info.setStore_menus(al_menu);
        menuAdapter.notifyDataSetChanged();
        ((SignActivity)getActivity()).getValue(store_info); // **
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        ((SignActivity) getActivity()).setImageUri(menuAdapter);
        menuAdapter.notifyDataSetChanged();

        super.onResume();
    }
}
