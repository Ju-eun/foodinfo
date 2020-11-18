package com.project.foodinfo.Sign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.foodinfo.R;

public class SignFragment extends Fragment {
    Spinner spinner;
    Button btn_sign_menu_plus;
    Button btn_sign_menu_minus;
    ListView lv_sign_menu;


    String[] names = {"한식", "중식", "일식", "기타"};
    public SignFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        spinner = view.findViewById(R.id.sign_frag_spinner_categori);
        lv_sign_menu = view.findViewById(R.id.sign_frag_lv_menulist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item, names
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"선택된 아이템 :" +names[position], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        btn_sign_menu_plus.setOnClickListener(mListener);
        btn_sign_menu_minus.setOnClickListener(mListener);

        return view;

    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == btn_sign_menu_plus.getId()){

            }
            else if(v.getId() == btn_sign_menu_minus.getId()){

            }
        }
    };


}
