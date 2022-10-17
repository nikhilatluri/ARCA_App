package com.example.arca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelpere extends SQLiteOpenHelper {
    public DBHelpere(Context context) {
        super(context, "Expenses.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE expenses( date TEXT, exp TEXT, d_exp TEXT, d_exp2 TEXT, qty TEXT, price TEXT, trans TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists expenses");
    }

    public Boolean insertuserdata( String date,String exp,String d_exp,String d_exp2,String qty,String price,String trans) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("exp", exp);
        contentValues.put("d_exp", d_exp);
        contentValues.put("d_exp2", d_exp2);
        contentValues.put("qty", qty);
        contentValues.put("price", price);
        contentValues.put("trans", trans);

        long result = DB.insert("expenses", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }



    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("expenses", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from expenses", null);
        return cursor;

    }
}
