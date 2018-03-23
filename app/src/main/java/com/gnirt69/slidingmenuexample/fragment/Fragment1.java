package com.gnirt69.slidingmenuexample.fragment;/**
 * Created by NgocTri on 10/18/2015.
 */

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.gnirt69.slidingmenuexample.Database.DBAction;
import com.gnirt69.slidingmenuexample.R;

import static java.lang.System.out;

public class Fragment1 extends Fragment {

    Button button, delbutton;
    private TextView textView, countDown;
    private EditText editText;
    private String getText;
    private SwipeMenuListView listView;
    private DBAction linkData;
    private Cursor getData;
    private int additem;
    private ListAdapter defaultAdapter;
    private int numOfTask = 0;
    private SwipeMenuCreator creator;

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
        listView = (SwipeMenuListView) rootView.findViewById(R.id.listview01);
        countDown = (TextView) rootView.findViewById(R.id.countdown);

        slideListener();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //偵測時間是否結束，以任務執行數回報
                if (numOfTask == 0) {

                    getText = editText.getText().toString();
                    linkData.InsertDBTable(getText);

                    numOfTask++;

                    new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long l) {  //首要任務
                            countDown.setText(String.valueOf(l / 1000));
                        }

                        @Override
                        public void onFinish() {    //回報
                            numOfTask--;
                            countDown.setText("");
                        }
                    }.start();
                }

                //將資料更新或顯示到View上
                defaultAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, linkData.GetAllData());
                initializeList();
                listView.setAdapter(defaultAdapter);

            }

        });


        delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkData.DeleteTable();

                defaultAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, linkData.GetAllData());
                initializeList();
                listView.setAdapter(defaultAdapter);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return true;
            }
        });

        defaultAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, linkData.GetAllData());
        initializeList();
        listView.setAdapter(defaultAdapter);

        return rootView;
    }

    //添加滑動效果
    private void initializeList() {
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(90);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
    }

    private void slideListener() {
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {

                    case 0:

                        out.println(listView.getAdapter().getItem(position));
                        break;

                    case 1:

                        out.println("clicked " + index);
                        break;
                }

                return false;
            }
        });
    }
}
