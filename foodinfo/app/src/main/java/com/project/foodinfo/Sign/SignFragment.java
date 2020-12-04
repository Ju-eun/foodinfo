package com.project.foodinfo.Sign;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.IDNA;
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
import androidx.fragment.app.FragmentActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class SignFragment extends Fragment implements View.OnClickListener {
    Spinner spinner;
    Button btn_sign_menu_plus;
    Button btn_sign_menu_minus;
    Button btn_oper_picture;
    ListView lv_sign_menu;
    Button sign_frag_btn_stcheck1;

    MemInfo.Store_Info store_info = new MemInfo.Store_Info();
    String str;
    EditText sign_frag_et_storename;
    EditText sign_frag_et_categori;
    EditText sign_frag_et_firstopen;
    EditText sign_frag_et_close;
    EditText sign_frag_et_memo;
    DatabaseReference Ref;
    MenuAdapter menuAdapter;
    Context context;
    String key;
    MemInfo value;
    String selected_item;
    boolean sign = false;

    String[] names = {"한식", "중식", "일식", "기타"};

    private boolean validate = false; // 중복체크용

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
        sign_frag_btn_stcheck1 = view.findViewById(R.id.sign_frag_btn_stcheck);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item, names
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),"선택된 아이템 :" +names[position], Toast.LENGTH_SHORT).show();
                selected_item = names[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sign_frag_btn_stcheck1.setOnClickListener(this);
        menuAdapter.addItem("", "", menu_size);
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


            if (v.getId() == btn_sign_menu_plus.getId()) {

                menuAdapter.addItem("", "", menu_size);
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
            } else if (v.getId() == btn_sign_menu_minus.getId()) {
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


    public void onTest() {
        ArrayList<Store_Menu> al_menu = new ArrayList<Store_Menu>();

        store_info.setStore_name(sign_frag_et_storename.getText().toString().trim());
        store_info.setStore_time(sign_frag_et_firstopen.getText().toString().trim() + " ~ " + sign_frag_et_close.getText().toString().trim());
        store_info.setStore_memo(sign_frag_et_memo.getText().toString().trim());
        store_info.setStore_name(sign_frag_et_storename.getText().toString().trim());
        if (selected_item.equals(names[3])) {
            store_info.setStore_category(sign_frag_et_categori.getText().toString().trim());
        } else {
            store_info.setStore_category(selected_item);
        }

        for (int i = 0; i < menuAdapter.getCount(); i++) {

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
        ((SignActivity) getActivity()).getValue(store_info); // **
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

    @Override
    public void onClick(View v) {
        Log.d("AA", "AASDads");
        //id 중복체크 버튼이 눌린 경우
        validate = true;



        Log.d("AA", "key : " + key);
        Ref = FirebaseDatabase.getInstance().getReference("moble-foodtruck").child("MemInfo");
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("AA", "key : " + key);
                boolean a = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        key = snapshot.getKey();
                        value = snapshot.getValue(MemInfo.class);
                        Log.d("AA", "key : " + key);

                        if (sign_frag_et_storename.getText().toString().equals(value.getStore_info().getStore_name())) {
                            Log.d("AA", "체크한번");
                            Toast.makeText(context, "중복된 가게이름이 있습니다.", Toast.LENGTH_SHORT).show();
                            a = true;
                            sign=true;
                            break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (a == false) {
                    Log.d("AA", "체크한번밑");
                    Toast.makeText(context, "사용할 수 있는 가게이름입니다.", Toast.LENGTH_SHORT).show();
                    sign=true;

                    Bundle bundle = new Bundle(1);
                    if (sign ==true) {
                        bundle.putString("key", "한식");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
