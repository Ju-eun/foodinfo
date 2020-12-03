package com.project.foodinfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Fragment_menu_user extends Fragment {

    private ListView lv_menu_storeinfo_user;
    private MyAdapter myAdapter12 = new MyAdapter();
    private Context context;

    String[] strasdasd = {"123", "12"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        context = container.getContext();

        view = inflater.inflate(R.layout.fragment_menu_user, container, false);

        lv_menu_storeinfo_user = (ListView) view.findViewById(R.id.lv_menu_storeinfo_user);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strasdasd);
        lv_menu_storeinfo_user.setAdapter(myAdapter12);

        ((StoreinfoActivityUser)context).getFragmentAdapter(myAdapter12);

        return view;
    }
}

