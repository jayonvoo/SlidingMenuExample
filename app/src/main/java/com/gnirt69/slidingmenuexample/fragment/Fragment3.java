package com.gnirt69.slidingmenuexample.fragment;/**
 * Created by NgocTri on 10/18/2015.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gnirt69.slidingmenuexample.R;

import java.util.ArrayList;

public class Fragment3 extends Fragment {

    Button button;
    TextView textView;
    EditText editText;
    String getText;
    ListView listView;
    ArrayList comments = new ArrayList<>();
    int additem;

    public Fragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);

        button = (Button) rootView.findViewById(R.id.button1);
        editText = (EditText) rootView.findViewById(R.id.editText1);
        listView = (ListView) rootView.findViewById(R.id.listview01);

        additem = 0;

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getText = editText.getText().toString();

                comments.add(getText);


                ListAdapter adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, comments);

                listView.setAdapter(adapter);

                additem++;

            }
        });

        return rootView;
    }
}
