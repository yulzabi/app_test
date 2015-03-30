package com.example.yyulazar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;


public class PersonListEditActivity extends Activity implements View.OnClickListener{

    private String listToEdit;
    private Button addPerson;
    private Button deleteList;
    private ListView personsListView;
    PersonsListDataSource database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list_edit);

        Bundle bundle = getIntent().getExtras();
        listToEdit = bundle.getString("personListName");
        Toast.makeText(this, listToEdit, Toast.LENGTH_LONG).show();

        addPerson = (Button) findViewById(R.id.bAddPersonPersonListEditActivity);
        deleteList = (Button) findViewById(R.id.bDeletePersonsListPersonListEditActivity);
        personsListView = (ListView) findViewById(R.id.lvPersonsListViewPersonEditListActivity);
        addPerson.setOnClickListener(this);
        deleteList.setOnClickListener(this);
        database = new PersonsListDataSource(this, listToEdit);
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating database", Toast.LENGTH_LONG).show();
            return;
        }
        personsListView.setAdapter(new ArrayAdapter<Person>(this,
                android.R.layout.simple_list_item_1, database.getAllPersons()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && !data.getExtras().isEmpty() && data.getExtras().containsKey("selectedContacts"))
        {
            Object[] objArray = (Object[]) data.getExtras().getSerializable("selectedContacts");
            String selectedContacts[][] = null;
            if(objArray != null)
            {
                selectedContacts = new String[objArray.length][];
                for(int i = 0; i < objArray.length; i++)
                {
                    selectedContacts[i] = (String[]) objArray[i];
                }
                //Now selectedContacts[] contains the selected contacts
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_list_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bAddPersonPersonListEditActivity:
                final int request_code = 1010;
                startActivityForResult(new Intent(PersonListEditActivity.this,ContactsPickerActivity.class),request_code);
                break;
            case R.id.bDeletePersonsListPersonListEditActivity:
                //TODO delete this list from all data base and go back to main screen
        }

    }
}
