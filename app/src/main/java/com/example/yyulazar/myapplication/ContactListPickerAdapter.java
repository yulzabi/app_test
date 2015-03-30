package com.example.yyulazar.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by yyulazar on 12/5/14.
 */
public class ContactListPickerAdapter extends BaseAdapter {

    private Context context;
    public ContactListWrapper allContactsList;
    public ContactListWrapper selectedContactsList;

    public ContactListPickerAdapter(Context context, ContactListWrapper list)
    {
        super();
        this.context = context;
        this.allContactsList = list;
        selectedContactsList = new ContactListWrapper();

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view_row = inflater.inflate(R.layout.contact_list_item_layout, viewGroup,false);

        CheckBox chk_contact = (CheckBox) view_row.findViewById(R.id.cbSelectContactItem);
        chk_contact.setId((int) allContactsList.getContacts().get(position).get_id());
        //TODO add the photo and init this

        //Text to display near checkbox [Here, Contact_Name (Number Label : Phone Number)]
        chk_contact.setText(allContactsList.getContacts().get(position).getName().toString() + " ( " +
                            allContactsList.getContacts().get(position).getPhoneNumberOffice() + " , " +
                            allContactsList.getContacts().get(position).getPhoneNumberHome().toString() + ")");

        //Set checkbox to true if already selected
        if(alreadySelected(allContactsList.getContacts().get(position)))
        {
            chk_contact.setChecked(true);
        }

        //Code to get Selected Contacts.
        chk_contact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {


                Contact t = allContactsList.getContact(arg0.getId());
                if(t != null && arg1)
                {
                    if(!alreadySelected(t))
                        selectedContactsList.addContact(t);
                }
                else if(!arg1 && t!=null)
                {
                    selectedContactsList.removeContact(arg0.getId());
                }


            }

        });

        return view_row;
    }

    private boolean alreadySelected(Contact contact) {
        if (selectedContactsList.getContact((int)contact.get_id()) != null)
            return true;
        return false;
    }

    @Override
    public int getCount() {
        return allContactsList.getCount();
    }

    @Override
    public Object getItem(int i) {
        return allContactsList.getContact(i);
    }

    @Override
    public long getItemId(int i) {
        return allContactsList.getContact(i).get_id();
    }


}
