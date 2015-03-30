package com.example.yyulazar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class ContactsPickerActivity extends Activity {

    private ContactListPickerAdapter adapter;
    private ListView listContacts;
    private EditText srchBox;
    Button btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_picker);
        listContacts = (ListView) findViewById(R.id.lvContactsListContactPickerActivity);
        adapter = new ContactListPickerAdapter(this, new ContactListWrapper());
        listContacts.setAdapter(adapter);
        try
        {Log.d("", "before create new Async");
            //Running AsyncLoader with adapter and blank filter
            new AsyncContactLoader(ContactsPickerActivity.this, adapter).execute("%");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        srchBox = (EditText) findViewById(R.id.etSearchContactContactsPickerActivity);

        //Adding text change listener for filtering contacts
        srchBox.addTextChangedListener(new TextWatcher(){
//        @Override
        public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        }

//        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
        }

//        @Override
        public void onTextChanged(CharSequence s, int start, int before,int count)
        {
            String filter = s.toString().trim()+"%";
            //Running AsyncLoader with adapter and search text as parameters
            try
            {
                new AsyncContactLoader(ContactsPickerActivity.this, adapter).execute(filter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    });//end of addTextChangeListener

        //Code to return selected contacts...
        btnDone = (Button) findViewById(R.id.bDonePickContactsPickerActivity);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                if(adapter.selectedContactsList.getCount() > 0)
                {
                    String[][] sel_cons = new String[adapter.selectedContactsList.getCount()][5];
                    for(int i = 0; i < adapter.selectedContactsList.getCount(); i++)
                    {
                        sel_cons[i][0] = String.valueOf(adapter.selectedContactsList.getContacts().get(i).get_id());
                        sel_cons[i][1] = adapter.selectedContactsList.getContacts().get(i).getName();
                        sel_cons[i][2] = adapter.selectedContactsList.getContacts().get(i).getPhoneNumberHome();
                        sel_cons[i][3] = adapter.selectedContactsList.getContacts().get(i).getPhoneNumberOffice();
                        sel_cons[i][4] = adapter.selectedContactsList.getContacts().get(i).getPhoneNumberMobile();
                    }
                    //Bundling up the contacts to pass
                    Bundle data_to_pass = new Bundle();
                    data_to_pass.putSerializable("selectedContacts", sel_cons);
                    intent.putExtras(data_to_pass);
                    setResult(RESULT_OK,intent);
                    Log.v("Result", "ok");
                }
                else
                {
                    //If user presses back button without selecting any contact
                    Log.v("Result", "cancelled");
                    setResult(RESULT_CANCELED,intent);
                }
                //Ending Activity and passing result
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_picker, menu);
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
}
