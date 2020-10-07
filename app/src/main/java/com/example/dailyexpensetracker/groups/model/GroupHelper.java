package com.example.dailyexpensetracker.groups.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dailyexpensetracker.groups.model.GroupsModel;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "GROUPS";
    private static final String COLUMN_GROUP_ID = "GROUP_ID";
    private static final String COLUMN_GROUP_NAME = "GROUP_NAME";


    public GroupHelper(@Nullable Context context) {
        super(context, "ExpenseTrackerDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_GROUP_ID + " INTEGER, " + COLUMN_GROUP_NAME + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean add(GroupsModel group){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GROUP_ID, group.getId());
        values.put(COLUMN_GROUP_NAME, group.getName());

        long insert = db.insert(TABLE_NAME, null, values);

        db.close();

        if(insert == -1){
            return false;
        }
        else
            return true;
    }

    public List<GroupsModel> getEveryOne(){


        List<GroupsModel> grouplist = new ArrayList<>();

        //get data from the database
        String querystring = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int groupid = cursor.getInt(1);
                String groupname = cursor.getString(2);

                GroupsModel group = new GroupsModel(groupid, groupname);
                grouplist.add(group);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return grouplist;
    }
}
