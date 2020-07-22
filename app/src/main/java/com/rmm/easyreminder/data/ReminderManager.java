package com.rmm.easyreminder.data;

import android.content.Context;

import com.rmm.easyreminder.database.Database;
import com.rmm.easyreminder.view.IReminders;

import java.util.ArrayList;

/**
 * @author Roberto
 * Class that helos managing some task related with reminders. It will be act as a Model for the IReminder view
 */
public class ReminderManager implements IReminders.IModel {

    private IReminders.IPresenter mPresenter;

    private Context   mContext;
    private Database mDatabase;

    /**
     * Creates a Reminder Manager.
     * @param presenter Reference to the component that will be act as a Presenter.
     * @param context Reference to the context of the application. This will be used by the database for its creation.
     */
    public ReminderManager (IReminders.IPresenter presenter, Context context)
    {
        mPresenter = presenter;
        mContext = context;

        mDatabase = new Database (mContext);
    }

    /**
     * Adds a new reminder in the database.
     * @param reminder The reminder to add to the database.
     */
    public void addReminder (Reminder reminder)
    {
        mDatabase.insert (reminder);
    }

    /**
     * Removes the reminder that matches with the id.
     * @param id The id of the reminder to remove.
     */
    public void removeReminder (int id)
    {
        mDatabase.remove (id);
    }

    /**
     * Retrieves the reminder that matches with the id.
     * @param id The id of the desired reminder.
     * @return The reminder that matches with the id.
     */
    public Reminder getReminder (int id)
    {
        Reminder reminder = null;

        reminder = mDatabase.getReminder (id);

        return reminder;
    }

    /**
     * Retrieves all the reminders stored in the database.
     * @return The list of reminders from the database.
     */
    @Override
    public ArrayList<Reminder> getReminders ()
    {
        ArrayList<Reminder> reminders = null;

        reminders = mDatabase.retrieveReminders();

        return reminders;
    }

    /**
     * Closes the database reference.
     */
    @Override
    public void onDestroy() {
        mDatabase.close();
    }
}
