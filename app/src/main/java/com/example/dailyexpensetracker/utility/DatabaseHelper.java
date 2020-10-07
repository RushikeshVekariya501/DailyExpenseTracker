package com.example.dailyexpensetracker.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dailyexpensetracker.groups.model.GroupModel;
import com.example.dailyexpensetracker.transaction.model.party.PartyModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DatabaseHelper {
    public static final String DATABASE_NAME = "ExpenseTrackerDB.db";
    public static final int DATABASE_VERSION = 1;

    //KEY COMMONS
    public static final String KEY_ID = "id";

    //Group table columns
    public static final String GROUP_TBL_NAME = "TBL_GROUP";
    public static final String GROUP_COL_GROUP_NAME = "GROUP_NAME";

    //Party table columns
    public static final String PARTY_TBL_NAME = "TBL_PARTY";
    public static final String PARTY_COL_PARTY_NAME = "PARTY_NAME";

    //Category table columns
    public static final String CATEGORY_TBL_NAME = "TBL_CATEGORY";
    public static final String CATEGORY_COL_CATEGORY_NAME = "CATEGORY_NAME";


    DatabaseManager dbManager;
    Context mContext;
    Cursor result = null;
    private static SQLiteDatabase mSqLiteDatabase;

    public DatabaseHelper(Context mContext) {
        this.mContext = mContext;
        dbManager = DatabaseManager.getInstance(mContext, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public void open() {
        if (mSqLiteDatabase == null || mSqLiteDatabase.isOpen())
            mSqLiteDatabase = dbManager.getWritableDatabase();
    }

    public void close() {

        mSqLiteDatabase.close();
    }

    //INSERT GROUP
    public Long insertGroup(GroupModel group) {
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, group.getId());
        values.put(GROUP_COL_GROUP_NAME, group.getName());

        return mSqLiteDatabase.insert(GROUP_TBL_NAME, null, values);

    }
    //GET GROUP
    public ArrayList<GroupModel> getGroupList() {
        ArrayList<GroupModel> mGroups = new ArrayList<>();

        try {
            result = mSqLiteDatabase.query(GROUP_TBL_NAME, new String[]{}, null, null,
                    null, null, null);

            if (result.moveToFirst()) {
                do {
                    GroupModel mGroup = new GroupModel();
                    mGroup.setId(result.getInt(0));
                    mGroup.setName(result.getString(1));
                    mGroups.add(mGroup);

                    Collections.sort(mGroups, new Comparator<GroupModel>() {
                        public int compare(GroupModel v1, GroupModel v2) {
                            return v1.getName().compareTo(v2.getName());
                        }
                    });

                } while (result.moveToNext());
            }

        } catch (Exception e) {

        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }
        return mGroups;
    }

    //INSERT PARTY
    public Long insertParty(PartyModel party) {
        ContentValues values = new ContentValues();
        values.put(PARTY_COL_PARTY_NAME, party.getName());
        long results =  mSqLiteDatabase.insert(PARTY_TBL_NAME, null, values);
return results;
    }

    //GET PARTY
    public ArrayList<PartyModel> getPartyList() {
        ArrayList<PartyModel> mParties = new ArrayList<>();

        try {
            result = mSqLiteDatabase.query(PARTY_TBL_NAME, new String[]{}, null, null,
                    null, null, null);

            if (result.moveToFirst()) {
                do {
                    PartyModel mParty = new PartyModel();
                    mParty.setId(result.getInt(0));
                    mParty.setName(result.getString(1));
                    mParties.add(mParty);

                    Collections.sort(mParties, new Comparator<PartyModel>() {
                        public int compare(PartyModel v1, PartyModel v2) {
                            return v1.getName().compareTo(v2.getName());
                        }
                    });

                } while (result.moveToNext());
            }

        } catch (Exception e) {

        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }
        return mParties;
    }
}
