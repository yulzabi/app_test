package com.example.yyulazar.myapplication;

import java.util.ArrayList;

/**
 * Created by yyulazar on 12/5/14.
 */
public class ContactListWrapper {

    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public int getCount()
    {
        return this.contacts.size();
    }
    public void addContact(Contact c)
    {
        this.contacts.add(c);
    }
    public void removeContact(Contact c)
    {
        this.contacts.remove(c);
    }
    public void removeContact(int id)
    {
        for(int i=0;i<this.getCount();i++)
        {
            if(id == this.contacts.get(i).get_id())
            {
                this.contacts.remove(this.contacts.get(i));
            }
        }
    }
    public Contact getContact(int id)
    {
        Contact tmp=null;
        for(int i=0;i<this.getCount();i++)
        {
            if(id == this.contacts.get(i).get_id())
            {
                tmp = new Contact();
                tmp.set_id(this.contacts.get(i).get_id());
                tmp.setName(this.contacts.get(i).getName());
                tmp.setPhoneNumberHome(this.contacts.get(i).getPhoneNumberHome());
                tmp.setPhoneNumberOffice(this.contacts.get(i).getPhoneNumberOffice());
                tmp.setThumbnail(this.contacts.get(i).getThumbnail());
                tmp.setPhoneNumberMobile(this.contacts.get(i).getPhoneNumberMobile());
            }
        }
        return tmp;
    }
    public ArrayList<Contact> getContacts()
    {
        return contacts;
    }
    public void setContacts(ArrayList<Contact> c)
    {
        this.contacts=c;
    }

}
