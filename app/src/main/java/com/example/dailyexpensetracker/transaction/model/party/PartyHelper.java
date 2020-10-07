package com.example.dailyexpensetracker.transaction.model.party;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PartyHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "PARTY";
    private static final String COLUMN_PARTY_ID = "PARTY_ID";
    private static final String COLUMN_PARTY_NAME = "PARTY_NAME";


    public PartyHelper(@Nullable Context context) {
        super(context, "ExpenseTrackerDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PARTY_ID + " INTEGER, " + COLUMN_PARTY_NAME + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean add(PartyModel group){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PARTY_ID, group.getId());
        values.put(COLUMN_PARTY_NAME, group.getName());

        long insert = db.insert(TABLE_NAME, null, values);

        db.close();

        if(insert == -1){
            return false;
        }
        else
            return true;
    }

    public List<PartyModel> getEveryOne(){


        List<PartyModel> grouplist = new ArrayList<>();

        //get data from the database
        String querystring = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int groupid = cursor.getInt(1);
                String groupname = cursor.getString(2);

                PartyModel group = new PartyModel(groupid, groupname);
                grouplist.add(group);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return grouplist;
    }
}
