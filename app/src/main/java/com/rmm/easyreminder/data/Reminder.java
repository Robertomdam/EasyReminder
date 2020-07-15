package com.rmm.easyreminder.data;

public class Reminder
{
    private int mId;
    private String mNote;

    public Reminder (String note) {
        mId =     -1;
        mNote = note;
    }

    public Reminder (int id, String note) {
          mId =   id;
        mNote = note;
    }

    public int getId() {
        return mId;
    }
    public String getNote() {
        return mNote;
    }
}
