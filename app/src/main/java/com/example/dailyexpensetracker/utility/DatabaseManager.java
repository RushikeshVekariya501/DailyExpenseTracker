package com.example.dailyexpensetracker.utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.view.menu.MenuBuilder;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static DatabaseManager sInstance = null;
    public static synchronized DatabaseManager getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext(), name, factory, version);
        }
        return sInstance;
    }

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE GROUP TABLE
        db.execSQL(create_group());
        //CREATE PARTY TABLE
        db.execSQL(create_party());
        //CREATE CATEGORY TABLE
        db.execSQL(create_category());

        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                arrTblNames.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }
        int i=0;

    }

    private String create_group() {

        return "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.GROUP_TBL_NAME
                + " ( " + DatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseHelper.GROUP_COL_GROUP_NAME + " TEXT);";
    }

    private String create_party() {

        return "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.PARTY_TBL_NAME
                + " ( " + DatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseHelper.PARTY_COL_PARTY_NAME + " TEXT);";
    }

    private String create_category() {

        return "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.CATEGORY_TBL_NAME
                + " ( " + DatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseHelper.CATEGORY_COL_CATEGORY_NAME + " TEXT);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
