package com.example.yyulazar.myapplication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yyulazar on 12/5/14.
 */
public class AsyncContactLoader extends AsyncTask {

    private ContactListPickerAdapter adapter;
    private ProgressDialog progress;
    private Context context;
    //Fields to select from database
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS, ContactsContract.Contacts.CONTACT_PRESENCE,
            ContactsContract.Contacts.PHOTO_ID, ContactsContract.Contacts.LOOKUP_KEY,};
    //Select from all rows
    String[] SELECT_ROWS = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };


    AsyncContactLoader(Context context, ContactListPickerAdapter adap) {
        //init AsyncLoader with the ListView Adapter
        Log.d("", "AsyncContactLoader()");
        adapter = adap;
        this.context = context;
    }

    protected void onPreExecute() {
        //Show a pop up message
        Log.d("", "AsyncContactLoader1()");
//        progress = ProgressDialog.show(context, "Please Wait", "Loading Contacts with Phone Numbers", true);
    }

    @Override
    protected Object doInBackground(Object[] object) {
//        android.os.Debug.waitForDebugger();
        Log.d("", "do in background");
        ContactListWrapper allContactsList = new ContactListWrapper();
//        Toast.makeText(context, "do in background", Toast.LENGTH_LONG).show();
        //Filter = text in search textbox
//        int r = 1 / 0;
        String filter = (String) object[0];
        ContentResolver cr = context.getContentResolver();
        int count = 0;

        //Code to fetch contacts...

        Uri uri = ContactsContract.Contacts.CONTENT_URI;



        /*Querying database (Select fields in projection from database where contact name like 'filter%',
        sort by name, in ascending order)*/
        Cursor cursor = cr.query(uri, SELECT_ROWS,
                null/*ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?"*/,
                null/*new String[]{filter.toString()}*/, null/*ContactsContract.Contacts.DISPLAY_NAME + " ASC"*/);

        Log.d("", "Contacts : " + cursor.getCount());
//        cursor.moveToFirst();
        while (cursor.moveToNext())//handle each contact
        {
            //Filtering Contacts with Phone Numbers
            Log.d("Async Thread", "in the while");
            int col = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            Log.d("Async Thread", "HAS_PHONE_NUMBER = " + col);
            if (Integer.parseInt(cursor.getString(col)) > 0) {
                Log.d("Async Thread", "has phone number");
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.d("Async Thread", "name " + name + " id "+ id);
                //Phone numbers lies in a separate table. Querying that table with Contact ID
                Cursor ph_cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

                Contact tmp = new Contact();
                tmp.set_id(Integer.parseInt(id));
                tmp.setName(name);
                while (ph_cur.moveToNext())//handle each phone number of contact
                {
                    String phId = ph_cur.getString(ph_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));

                    //Label eg : home, office etc. They are stored as int values
                    int labelType = ph_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL);
                    String ph_no = ph_cur.getString(ph_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    switch (labelType) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            tmp.setPhoneNumberHome(ph_no);
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            tmp.setPhoneNumberMobile(ph_no);
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            tmp.setPhoneNumberOffice(ph_no);
                            break;
                    }
                }
                InputStream photo = new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                };
                count++;
                //Refresh ListView upon loading 100 Contacts
                allContactsList.addContact(tmp);
                if (count == 100) {
                    publishProgress(allContactsList);
                    count = 0;
                }
                ph_cur.close();
            }
        }
        cursor.close();
        Log.v("","finish while Async got " + allContactsList.getCount());
        return allContactsList;
    }

    //Code to refresh list view
//    @Override
    protected void onProgressUpdate(ContactListWrapper... glsts )
    {
        if(progress.isShowing())
            progress.dismiss();
        adapter.allContactsList = glsts[0];
        adapter.notifyDataSetChanged();
        Log.v("Progress", adapter.allContactsList.getCount()+" loaded");
    }

//    @Override
    //Loading contacts finished, refresh list view to load any missed out contacts
    protected void onPostExecute(ContactListWrapper result)
    {
        Log.v("Progress ::", adapter.allContactsList.getCount()+" total loaded");
        if(progress.isShowing())
            progress.dismiss();
        adapter.allContactsList=result;
        adapter.notifyDataSetChanged();
        Log.v("Progress ::", adapter.allContactsList.getCount()+" total loaded");
//        Toast.makeText(ContactPicker.this, cla.getCount() + " Contact(s) Found", Toast.LENGTH_LONG).show();
    }



}

