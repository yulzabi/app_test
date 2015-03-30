package com.example.yyulazar.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyulazar on 11/28/14.
 */
public class PersonsListDataSource {
    // Database fields
    private SQLiteDatabase database;
    private PersonsListSQLHelper dbHelper;
    private Context context;
    private String[] allColumns = {
            PersonsListSQLHelper.COLUMN_ID,
            PersonsListSQLHelper.COLUMN_PERSON_NAME,
            PersonsListSQLHelper.COLUMN_PERSON_PHONE,
            PersonsListSQLHelper.COLUMN_PERSON_AVAILABLE};

    public PersonsListDataSource(Context context, String tableName) {
        dbHelper = new PersonsListSQLHelper(context, tableName);
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

    public Person createPersonListName(String personName, String personPhone, int personAvailable) {
        ContentValues values = new ContentValues();
        values.put(PersonsListSQLHelper.COLUMN_PERSON_NAME, personName);
        values.put(PersonsListSQLHelper.COLUMN_PERSON_PHONE, personPhone);
        values.put(PersonsListSQLHelper.COLUMN_PERSON_AVAILABLE, personAvailable);
        getTablesNames();
        long insertId = database.insert(dbHelper.TABLE_PERSON_NAME_LISTS, null,
                values);
        Cursor cursor = database.query(dbHelper.TABLE_PERSON_NAME_LISTS,
                allColumns, PersonsListSQLHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Person newPerson = cursorToPerson(cursor);
        cursor.close();
        return newPerson;
    }

    public void deletePersonListName(MyListForSQL personListName) {
        long id = personListName.getId();
        System.out.println("PersonListName deleted with id: " + id);
        database.delete(dbHelper.TABLE_PERSON_NAME_LISTS, PersonsListSQLHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Person> getAllPersons() {
        List<Person> PersonListsNames = new ArrayList<Person>();

        Cursor cursor = database.query(dbHelper.TABLE_PERSON_NAME_LISTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Person person = cursorToPerson(cursor);
            PersonListsNames.add(person);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return PersonListsNames;
    }

    private Person cursorToPerson(Cursor cursor) {
        Person person = new Person();
        person.setId(cursor.getLong(0));
        person.setName(cursor.getString(1));
        person.setPhone(cursor.getString(2));
        person.setAvailable((cursor.getInt(3) == 1) ? true : false);
        return person;
    }
}
