package com.rmm.easyreminder.data;

import android.content.Context;
import android.util.Log;

import com.rmm.easyreminder.database.Database;

import java.util.ArrayList;

public class ReminderManager {

    private Context   mContext;
    private Database mDatabase;

    public ReminderManager (Context context)
    {
        mContext = context;

        mDatabase = new Database (mContext);
    }

    public void add (Reminder reminder)
    {
        mDatabase.insert (reminder);
    }

    public void remove (int id)
    {
        Log.d("DEBUGGING", "remove: " + id);
        mDatabase.remove (id);
    }

    public Reminder getReminder (int id)
    {
        Reminder reminder = null;

        reminder = mDatabase.getReminder (id);

        return reminder;
    }

    public ArrayList<Reminder> getReminders ()
    {
        ArrayList<Reminder> reminders = null;

        reminders = mDatabase.retrieveReminders();

        return reminders;
    }

    public void destroy () { mDatabase.close(); }
}
