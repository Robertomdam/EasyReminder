package com.rmm.easyreminder.data;

import android.content.Context;
import android.util.Log;

import com.rmm.easyreminder.database.Database;
import com.rmm.easyreminder.view.IReminders;

import java.util.ArrayList;

public class ReminderManager implements IReminders.IModel {

    private IReminders.IPresenter mPresenter;

    private Context   mContext;
    private Database mDatabase;

    public ReminderManager (IReminders.IPresenter presenter, Context context)
    {
        mPresenter = presenter;
        mContext = context;

        mDatabase = new Database (mContext);
    }

    public void addReminder (Reminder reminder)
    {
        mDatabase.insert (reminder);
    }

    public void removeReminder (int id)
    {
        mDatabase.remove (id);
    }

    public Reminder getReminder (int id)
    {
        Reminder reminder = null;

        reminder = mDatabase.getReminder (id);

        return reminder;
    }

    @Override
    public ArrayList<Reminder> getReminders ()
    {
        ArrayList<Reminder> reminders = null;

        reminders = mDatabase.retrieveReminders();

        return reminders;
    }

    @Override
    public void onDestroy() {
        mDatabase.close();
    }
}
