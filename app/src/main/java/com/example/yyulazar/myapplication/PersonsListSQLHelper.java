package com.example.yyulazar.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yyulazar on 11/28/14.
 */
public class PersonsListSQLHelper extends SQLiteOpenHelper {
    public String TABLE_PERSON_NAME_LISTS;// = "persons";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PERSON_NAME = "personName";
    public static final String COLUMN_PERSON_PHONE = "personPhone";
    public static final String COLUMN_PERSON_AVAILABLE= "personAvailable";
//    public static final String COLUMN_LIST_NAME = "personName";

    private static final String DATABASE_NAME = "personsList.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private String DATABASE_CREATE;

    public PersonsListSQLHelper(Context context, String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        TABLE_PERSON_NAME_LISTS = tableName;
        DATABASE_CREATE = "create table "
                + TABLE_PERSON_NAME_LISTS + " (" +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_PERSON_NAME + " text not null, " +
                COLUMN_PERSON_PHONE + " text not null, " +
                COLUMN_PERSON_AVAILABLE + " BOOLEAN)";

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
