package com.example.yyulazar.myapplication;

/**
 * Created by yyulazar on 11/29/14.
 */
public class Person {
    private long id;
    private String name;
    private String phone;
    private boolean available;

    Person(){}

    Person(long id, String name, String phone, boolean available)
    {
        this.setId(id);
        this.setName(name);
        this.setPhone(phone);
        this.setAvailable(available);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return name + " " + phone + " " + ((available) ? " is available" : " is available") ;
    }
}
