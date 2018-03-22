package com.gnirt69.slidingmenuexample.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiyot on 3/16/2018.
 */

public class DBAction {

    private MyDBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    Cursor cursor;

    public DBAction(Context context) {
        this.context = context;
    }

    public void InsertDBTable(String comment) {
        dbHelper = new MyDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("insert into data_table(cmd) values('" + comment + "')");

    }

    public List<String> GetAllData() {
        List<String> pakaging = new ArrayList();
        dbHelper = new MyDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from data_table", null);

        if (cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                pakaging.add(cursor.getString(cursor.getColumnIndex("cmd")));
            }
        }
        return pakaging;
    }

    public void DeleteData(List<String> data, int position) {

        int id;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from data_table", null);

        data.remove(position);
        cursor.moveToPosition(position);
        id = cursor.getInt(cursor.getColumnIndex("_id"));

    }

    public void DeleteTable() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //db.rawQuery("drop table if exists data_table", null);
        db.delete("data_table", null, null);
    }


}
