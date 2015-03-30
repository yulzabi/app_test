package com.example.yyulazar.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyulazar on 11/25/14.
 */
public class PersonListsNamesDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MyPersonListsNamesSQLHelper dbHelper;
    private Context context;
    private String[] allColumns = { MyPersonListsNamesSQLHelper.COLUMN_ID,
            MyPersonListsNamesSQLHelper.COLUMN_LIST_NAME };

    public PersonListsNamesDataSource(Context context) {
        dbHelper = new MyPersonListsNamesSQLHelper(context);
        this.context = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void getTablesNames()
    {
        Cursor c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) while (!c.isAfterLast()) {
            Log.d("get table name", "Table Name=> " + c.getString(0));
            c.moveToNext();
        }
    }

    public MyListForSQL createPersonListName(String listName) {
        ContentValues values = new ContentValues();
        values.put(MyPersonListsNamesSQLHelper.COLUMN_LIST_NAME, listName);
        getTablesNames();
        long insertId = database.insert(MyPersonListsNamesSQLHelper.TABLE_PERSON_NAME_LISTS, null,
                values);
        Cursor cursor = database.query(MyPersonListsNamesSQLHelper.TABLE_PERSON_NAME_LISTS,
                allColumns, MyPersonListsNamesSQLHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MyListForSQL newPersonListName = cursorToComment(cursor);
        cursor.close();
        return newPersonListName;
    }

    public void deletePersonListName(MyListForSQL personListName) {
        long id = personListName.getId();
        System.out.println("PersonListName deleted with id: " + id);
        database.delete(MyPersonListsNamesSQLHelper.TABLE_PERSON_NAME_LISTS, MyPersonListsNamesSQLHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<MyListForSQL> getAllPersonListsNames() {
        List<MyListForSQL> PersonListsNames = new ArrayList<MyListForSQL>();

        Cursor cursor = database.query(MyPersonListsNamesSQLHelper.TABLE_PERSON_NAME_LISTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyListForSQL personListName = cursorToComment(cursor);
            PersonListsNames.add(personListName);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return PersonListsNames;
    }

    private MyListForSQL cursorToComment(Cursor cursor) {
        MyListForSQL personListName = new MyListForSQL();
        personListName.setId(cursor.getLong(0));
        personListName.setComment(cursor.getString(1));
        return personListName;
    }
}
