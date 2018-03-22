package com.gnirt69.slidingmenuexample.fragment;/**
 * Created by NgocTri on 10/18/2015.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gnirt69.slidingmenuexample.Database.DBAction;
import com.gnirt69.slidingmenuexample.MainActivity;
import com.gnirt69.slidingmenuexample.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment1 extends Fragment {

    Button button, delbutton;
    private TextView textView, countDown;
    private EditText editText;
    private String getText;
    private ListView listView;
    private ArrayList comments;
    private DBAction linkData;
    private Cursor getData;
    private int additem;
    private ListAdapter defaultAdapter;
    private Timer timer;
    int timeS = 30;

    public Fragment1() {

    }

    @SuppressLint("ValidFragment")
    public Fragment1(Context context) {
        linkData = new DBAction(context);
    }


    //创建或初始化介面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);


        button = (Button) rootView.findViewById(R.id.button1);
        delbutton = (Button) rootView.findViewById(R.id.button2);
        editText = (EditText) rootView.findViewById(R.id.editText1);
        listView = (ListView) rootView.findViewById(R.id.listview01);
        countDown = (TextView) rootView.findViewById(R.id.countdown);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getText = editText.getText().toString();
                comments = new ArrayList<>();
                timer = new Timer();
                comments.add(getText);
                linkData.InsertDBTable(getText);

                defaultAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, linkData.GetAllData());
                listView.setAdapter(defaultAdapter);

                SubInTime();

            }
        });

        delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkData.DeleteTable();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return true;
            }
        });

        //將資料顯示到listview上
        defaultAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, linkData.GetAllData());
        listView.setAdapter(defaultAdapter);

        return rootView;
    }


    //時間計數器
    private int SubInTime() {

        int delay = 1000;
        int period = 1000;
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (timeS == 1) {
                    timer.cancel();
                } else {
                    timeS--;
                    countDown.setText(String.valueOf(timeS));

                }
            }
        }, delay, period);
        return timeS;
    }
}
