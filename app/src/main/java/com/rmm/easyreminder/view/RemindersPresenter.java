package com.rmm.easyreminder.view;

import android.content.Context;

import com.rmm.easyreminder.R;
import com.rmm.easyreminder.data.Reminder;
import com.rmm.easyreminder.data.ReminderManager;

/**
 * @author Roberto
 * Class that acts as the presenter of the RemindersActivity in a MVP pattern.
 */
public class RemindersPresenter implements IReminders.IPresenter {

    private IReminders.IView   mView;
    private IReminders.IModel mModel;

    private Context mContext;

    /**
     * Sets basic data and creates the model.
     * @param view Reference to the view interface.
     * @param context Reference to the app context.
     */
    public RemindersPresenter (IReminders.IView view, Context context)
    {
        mView = view;
        mModel = new ReminderManager (this, context);

        mContext = context;
    }

    /**
     * Initializes the view.
     */
    @Override
    public void onCreate() {
        mView.init( mModel.getReminders() );
    }

    /**
     * Refreshes the view.
     */
    @Override
    public void onPause() {
        refreshView();
    }

    /**
     * Tells the model to be destroyed.
     */
    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }

    /**
     * Callback method that tells the model to add a new reminder.
     * @param reminderNote The note of the new reminder.
     */
    @Override
    public void onAddReminderButtonClick(String reminderNote) {

        if (reminderNote.length() == 0)
            return;

        Reminder reminder = new Reminder (reminderNote);

        mModel.addReminder (reminder);
        refreshView();

        mView.sendFeedbackToast( mContext.getResources().getString (R.string.add_reminder) );
    }

    /**
     * Callback method the tells the model to remove a reminder.
     * @param listIndex The index of the item that was selected.
     */
    @Override
    public void onRemoveReminderButtonClick (int listIndex) {
        int id = mModel.getReminders().get(listIndex).getId();

        mModel.removeReminder (id);
        refreshView();

        mView.sendFeedbackToast( mContext.getResources().getString (R.string.remove_reminder) );
    }

    /**
     * Callback method that tells the view to handle a notification.
     * @param listIndex The index of the button that was clicked.
     */
    @Override
    public void onSendNotificationButtonClick (int listIndex) {
        Reminder reminder = mModel.getReminders().get(listIndex);

        mView.sendNotification (reminder.getId(), reminder.getNote());
    }

    /**
     * Refreshes the view.
     */
    void refreshView ()
    {
        mView.refresh (mModel.getReminders());
    }
}
