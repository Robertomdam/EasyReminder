package com.rmm.easyreminder.view;

import android.widget.ArrayAdapter;

import androidx.annotation.CallSuper;

import com.rmm.easyreminder.data.Reminder;

import java.util.ArrayList;

public interface IReminders {

    public interface IView
    {
        void init (ArrayList<Reminder> reminders);
        void refresh (ArrayList<Reminder> reminders);
        void sendNotification (int id, String text);

        void sendFeedbackToast (String text);
    }

    public interface IPresenter
    {
        void onCreate  ();
        void onPause   ();
        void onDestroy ();

        void onAddReminderButtonClick (String reminderNote);
        void onRemoveReminderButtonClick (int listIndex);
        void onSendNotificationButtonClick (int listIndex);
    }

    public interface IModel
    {
        void onDestroy ();

        void addReminder (Reminder reminder);
        void removeReminder (int id);
        ArrayList<Reminder> getReminders ();
    }

}
