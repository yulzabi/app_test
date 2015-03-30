package com.example.yyulazar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InvalidObjectException;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button newListButton;
    private Button showListButton;
    private Button showStatisticsButton;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();



    }

    private void initialize() {
        newListButton = (Button) findViewById(R.id.bMainActivityCreateNewList);
        showListButton = (Button) findViewById(R.id.bMainActivityShowList);
        showStatisticsButton = (Button) findViewById(R.id.bMainActivityShowStatistic);
        newListButton.setOnClickListener(this);
        showListButton.setOnClickListener(this);
        showStatisticsButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Toast.makeText(this, "good", Toast.LENGTH_LONG).show();
        switch (view.getId())
        {
            case R.id.bMainActivityCreateNewList:
                //TODO move to new activity
                Intent i = new Intent(getApplicationContext(), NewListActivity.class);
                startActivity(i);
                break;
            case R.id.bMainActivityShowList:
                //TODO move to new activity
                break;
            case R.id.bMainActivityShowStatistic:
                //TODO move to new activity
                break;
        }
    }
}
