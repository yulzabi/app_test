package com.example.yyulazar.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yyulazar on 11/25/14.
 */
public class MyPersonListsNamesSQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_PERSON_NAME_LISTS = "personNameLists";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LIST_NAME = "listName";

    private static final String DATABASE_NAME = "personNameLists.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PERSON_NAME_LISTS + " (" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LIST_NAME
            + " text not null)";


    public MyPersonListsNamesSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
