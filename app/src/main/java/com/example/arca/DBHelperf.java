package com.example.arca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperf extends SQLiteOpenHelper {
    public DBHelperf(Context context)
    {
        super(context, "Feed.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Feed(date TEXT, time TEXT, tank TEXT, qty TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Feed");
    }

    public Boolean insertuserdata(String date, String time, String tank, String qty) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("date", date);
        contentValues1.put("time", time);
        contentValues1.put("tank", tank);
        contentValues1.put("qty", qty);
        long result = DB.insert("Feed", null, contentValues1);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }



    public Boolean deletedata(String date) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("delete from Userdata order by date desc limit 1;");
        return true;
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Feed", null);
        return cursor;

    }
}
