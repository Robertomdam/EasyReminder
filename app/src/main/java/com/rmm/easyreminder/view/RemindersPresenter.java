package com.rmm.easyreminder.view;

import android.content.Context;

import com.rmm.easyreminder.R;
import com.rmm.easyreminder.data.Reminder;
import com.rmm.easyreminder.data.ReminderManager;

public class RemindersPresenter implements IReminders.IPresenter {

    private IReminders.IView   mView;
    private IReminders.IModel mModel;

    private Context mContext;

    public RemindersPresenter (IReminders.IView view, Context context)
    {
        mView = view;
        mModel = new ReminderManager (this, context);

        mContext = context;
    }

    @Override
    public void onCreate() {
        mView.init( mModel.getReminders() );
    }

    @Override
    public void onPause() {
        refreshView();
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }

    @Override
    public void onAddReminderButtonClick(String reminderNote) {

        if (reminderNote.length() == 0)
            return;

        Reminder reminder = new Reminder (reminderNote);

        mModel.addReminder (reminder);
        refreshView();

        mView.sendFeedbackToast( mContext.getResources().getString (R.string.add_reminder) );
    }

    @Override
    public void onRemoveReminderButtonClick (int listIndex) {
        int id = mModel.getReminders().get(listIndex).getId();

        mModel.removeReminder (id);
        refreshView();

        mView.sendFeedbackToast( mContext.getResources().getString (R.string.remove_reminder) );
    }

    @Override
    public void onSendNotificationButtonClick (int listIndex) {
        Reminder reminder = mModel.getReminders().get(listIndex);

        mView.sendNotification (reminder.getId(), reminder.getNote());
    }

    void refreshView ()
    {
        mView.refresh (mModel.getReminders());
    }
}
