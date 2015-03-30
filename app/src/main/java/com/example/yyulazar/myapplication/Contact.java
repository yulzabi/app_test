package com.example.yyulazar.myapplication;

import java.io.InputStream;

/**
 * Created by yyulazar on 12/5/14.
 */
public class Contact {

    private String name;
    private String phoneNumberOffice;
    private String phoneNumberHome;
    private String phoneNumberMobile;
    private InputStream thumbnail;
    private int _id;

    public Contact(){}

    public Contact(String name, String phoneNumberOffice, String phoneNumberHome,
                   InputStream thumbnail, int id)
    {
        this.name = name;
        this.phoneNumberOffice = phoneNumberOffice;
        this.phoneNumberHome = phoneNumberHome;
        this.thumbnail = thumbnail;
        this._id = id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(InputStream thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPhoneNumberOffice() {
        return phoneNumberOffice;
    }

    public void setPhoneNumberOffice(String phoneNumberOffice) {
        this.phoneNumberOffice = phoneNumberOffice;
    }

    public String getPhoneNumberHome() {
        return phoneNumberHome;
    }

    public void setPhoneNumberHome(String phoneNumberHome) {
        this.phoneNumberHome = phoneNumberHome;
    }

    public String getPhoneNumberMobile() {
        return phoneNumberMobile;
    }

    public void setPhoneNumberMobile(String phoneNumberMobile) {
        this.phoneNumberMobile = phoneNumberMobile;
    }

    @Override
    public String toString() {
        return "Contact [name=" + name + ", phoneNumberOffice=" + phoneNumberOffice
                + ", phoneNumberHome=" + phoneNumberHome + ", phoneNumberMobile=" + phoneNumberMobile
                + ", thumbnail=" + thumbnail + ", _id=" + _id + "]";
    }
}
