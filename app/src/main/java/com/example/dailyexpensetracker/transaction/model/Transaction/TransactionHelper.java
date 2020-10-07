package com.example.dailyexpensetracker.transaction.model.Transaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TransactionHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "TRANSACTIONS";
    private static final String COLUMN_TRANSACTION_ID = "ID";
    private static final String COLUMN_TRANSACTION_NAME = "DATE";
    private static final String COLUMN_TRANSACTION_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_TRANSACTION_AMOUNT = "AMOUNT";
    private static final String COLUMN_TRANSACTION_IMAGE = "IMAGE";


    public TransactionHelper(@Nullable Context context) {
        super(context, "ExpenseTrackerDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TRANSACTION_ID + " INTEGER, " + COLUMN_TRANSACTION_NAME + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean add(TransactionModel group){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_ID, group.getId());
        //values.put(COLUMN_TRANSACTION_NAME, group.getName());

        long insert = db.insert(TABLE_NAME, null, values);

        db.close();

        if(insert == -1){
            return false;
        }
        else
            return true;
    }

    public List<TransactionModel> getEveryOne(){


        List<TransactionModel> grouplist = new ArrayList<>();

        //get data from the database
        String querystring = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int groupid = cursor.getInt(1);
                String groupname = cursor.getString(2);

                //TransactionModel group = new TransactionModel(groupid, groupname);
                //grouplist.add(group);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return grouplist;
    }
}
