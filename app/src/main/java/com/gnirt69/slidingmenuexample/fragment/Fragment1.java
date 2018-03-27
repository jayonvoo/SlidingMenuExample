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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.gnirt69.slidingmenuexample.Database.DBAction;
import com.gnirt69.slidingmenuexample.R;

import java.util.ArrayList;

import static java.lang.System.out;

@SuppressLint("ValidFragment")
public class Fragment1 extends Fragment {

    Button button, delbutton;
    String[] items;
    private TextView countDown;
    private EditText editText;
    private String getText, transferData;
    private SwipeMenuListView listView;
    private ArrayList<String> list;
    private DBAction linkData;
    private ArrayAdapter<String> defaultAdapter;
    private int numOfTask = 0;
    private SwipeMenuCreator creator;
    private boolean editable = false;


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

        //查詢功能初始化
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override

            //取得資料庫資料, 用於比對之後的資料篩選功能
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                items = new String[linkData.GetAllData().size()];

                for (int j = 0; j < linkData.GetAllData().size(); j++) {
                    items[j] = linkData.GetAllData().get(j);
                }

                searchItem(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //判斷是否可修改狀態
                if (editable) {

                    getText = editText.getText().toString();

                    linkData.UpdateTable(transferData, getText);

                    editable = false;

                } else if (numOfTask == 0) {  //偵測時間是否結束，以任務執行數回報


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

        //刪除資料表
        delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkData.DeleteTable();

                defaultAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, linkData.GetAllData());
                initializeList();
                listView.setAdapter(defaultAdapter);
            }
        });

        defaultAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, linkData.GetAllData());
        initializeList();
        listView.setAdapter(defaultAdapter);

        return rootView;
    }

    private void searchItem(String s) {
        list = linkData.GetAllData();

        //當留言為空時,跳過此判斷且重新讀取資料庫
        if (!s.equals("")) {

            //依序查詢每次輸入的關鍵字，並把相對的字串存到list裡面
            for (String item : items) {
                if (!item.contains(s)) {
                    list.remove(item);

                    for (Object obj : list) {
                        out.println(obj);
                    }

                    //只顯示list裡面的資料
                    defaultAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
                }
            }
        } else {

            //讀取資料庫
            defaultAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, linkData.GetAllData());
        }

        listView.setAdapter(defaultAdapter);
    }


    //添加滑動效果
    private void initializeList() {
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem editText = new SwipeMenuItem(getActivity());

                editText.setBackground(new ColorDrawable(Color.rgb(20, 174, 41)));

                editText.setWidth(50);

                editText.setIcon(R.drawable.ic_edit_black_24dp);


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(50);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu

                menu.addMenuItem(editText);
                menu.addMenuItem(deleteItem);

            }
        };
        listView.setMenuCreator(creator);
    }

    //ListMenu滑動的初始化功能
    private void slideListener() {
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {

                    case 0:

                        editable = true;
                        transferData = (String) listView.getAdapter().getItem(position);

                        //調到指定的位置(edit_text)
                        editText.requestFocus();
                        break;

                    case 1:

                        linkData.DeleteData((String) listView.getAdapter().getItem(position));
                        break;
                }

                defaultAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, linkData.GetAllData());
                initializeList();
                listView.setAdapter(defaultAdapter);

                return false;
            }
        });
    }
}
