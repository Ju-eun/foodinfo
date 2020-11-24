package com.project.foodinfo.Sign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.foodinfo.R;

public class SignFragment extends Fragment {
    Spinner spinner;
    Button btn_sign_menu_plus;
    Button btn_sign_menu_minus;
    Button btn_oper_picture;
    ListView lv_sign_menu;
    MenuAdapter menuAdapter;

    TextView sign_frag_storename;
    TextView sign_frag_category;
    TextView sign_frag_et_firstopen;
    TextView sign_frag_et_close;
    TextView sign_frag_memo;

    Context context;



    String[] names = {"한식", "중식", "일식", "기타"};
    public SignFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        context = container.getContext();
        spinner = view.findViewById(R.id.sign_frag_spinner_categori);
        lv_sign_menu = view.findViewById(R.id.sign_frag_lv_menulist);
        btn_sign_menu_plus = view.findViewById(R.id.sign_frag_btn_plus);
        btn_sign_menu_minus = view.findViewById(R.id.sign_frag_btn_minus);
        btn_oper_picture = view.findViewById(R.id.sign_frag_btn_picture);

        sign_frag_storename = view.findViewById(R.id.sign_frag_storename);
        sign_frag_et_firstopen = view.findViewById(R.id.sign_frag_et_firstopen);
        sign_frag_category = view.findViewById(R.id.sign_frag_category);
        sign_frag_et_close = view.findViewById(R.id.sign_frag_et_close);
        sign_frag_memo = view.findViewById(R.id.sign_frag_memo);


        sign_frag_et_firstopen = view.findViewById(R.id.sign_frag_et_firstopen);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item, names
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),"선택된 아이템 :" +names[position], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        menuAdapter = new MenuAdapter();
        menuAdapter.addItem("","");

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
                menuAdapter.addItem("","");

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
                lv_sign_menu.requestLayout();


                menuAdapter.notifyDataSetChanged();
            }
            else if(v.getId() == btn_sign_menu_minus.getId()){
                menuAdapter.removeItem();

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
                lv_sign_menu.requestLayout();

                menuAdapter.notifyDataSetChanged();
            }
        }
    };

    public void getFragmentValue(){
        String storename = sign_frag_storename.getText().toString();
        String category = sign_frag_category.getText().toString();
        String firstopen = sign_frag_et_firstopen.getText().toString();
        String close = sign_frag_et_close.getText().toString();
        String memo = sign_frag_memo.getText().toString();


//        ((SignActivity)getActivity()).setFragmentValue(storename, category, firstopen, close, memo);
    }
}
