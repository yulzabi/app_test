package com.example.yyulazar.myapplication;

/**
 * Created by yyulazar on 11/25/14.
 */
public class MyListForSQL {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return name;
    }

    public void setComment(String name) {
        this.name = name;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return name;
    }
}
