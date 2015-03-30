package com.example.yyulazar.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import static android.app.AlertDialog.Builder;


public class NewListActivity extends Activity implements View.OnClickListener{

    private PersonListsNamesDataSource personListsNamesDataSource;
    private Spinner personListNamesSpinner;
    private Button createNewListButton;
    private Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        //init all views
        personListNamesSpinner = (Spinner) findViewById(R.id.spinnerPersonListsName);
        createNewListButton= (Button) findViewById(R.id.bNewListActivityCreateNewList);
        proceed = (Button) findViewById(R.id.bNewListActivityProceed);

        //init data base
        personListsNamesDataSource = new PersonListsNamesDataSource(this);
        try {
            personListsNamesDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating database", Toast.LENGTH_LONG).show();
            return;
        }
        personListsNamesDataSource.getTablesNames();
        setSpinnerList();
        createNewListButton.setOnClickListener(this);
        proceed.setOnClickListener(this);
    }

    private void setSpinnerList()
    {
        List<MyListForSQL> values = personListsNamesDataSource.getAllPersonListsNames();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<MyListForSQL> adapter = new ArrayAdapter<MyListForSQL>(this,
                android.R.layout.simple_list_item_1, values);
        personListNamesSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_list, menu);
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

    private void moveToEditListActivity()
    {
        Intent i = new Intent(this, PersonListEditActivity.class);
        i.putExtra("personListName", personListNamesSpinner.getSelectedItem().toString());
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bNewListActivityCreateNewList:
                Builder createNewListDialog = new Builder(NewListActivity.this);
                createNewListDialog.setTitle("Create New Person List").setMessage("Enter new name");
                LinearLayout lp = new LinearLayout(NewListActivity.this);
                lp.setOrientation(LinearLayout.VERTICAL);
                final EditText inputListName = new EditText(NewListActivity.this);
                inputListName.setHint("list name");
                lp.addView(inputListName);
                createNewListDialog.setView(lp);
                createNewListDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        personListsNamesDataSource.createPersonListName(inputListName.getText().toString());
                        setSpinnerList();
                        personListNamesSpinner.setSelection(personListNamesSpinner.getCount() - 1);
                        moveToEditListActivity();
                    }
                });
                createNewListDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                createNewListDialog.show();
                break;
            case R.id.bNewListActivityProceed:
                moveToEditListActivity();
        }
    }
}
