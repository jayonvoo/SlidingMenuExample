package com.gnirt69.slidingmenuexample.Database;

import android.content.ContentValues;
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

    public ArrayList<String> GetAllData() {
        ArrayList<String> pakaging = new ArrayList();
        dbHelper = new MyDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from data_table", null);

        if (cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                pakaging.add(0, cursor.getString(cursor.getColumnIndex("cmd"))); //佇列顯示資料
            }
        }
        return pakaging;
    }

    public void UpdateTable(String string, String getText) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("cmd", getText);

        db.update("data_table", cv, "cmd=?", new String[]{string});

        //db.update("data_table", cv, null, null);
    }

    public void DeleteData(String string) {

        int id;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //db.rawQuery("delete from data_table where cmd = '" + string + "'");
        db.delete("data_table", "cmd=?", new String[]{string});

    }

    public void DeleteTable() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //db.rawQuery("drop table if exists data_table", null);
        db.delete("data_table", null, null);
    }


}
