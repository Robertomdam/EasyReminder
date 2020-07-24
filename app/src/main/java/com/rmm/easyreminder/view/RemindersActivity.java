package com.rmm.easyreminder.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rmm.easyreminder.R;
import com.rmm.easyreminder.data.Reminder;
import com.rmm.easyreminder.data.ReminderManager;

import java.util.ArrayList;

/**
 * @author Roberto
 * Launcher activity of the app. It handles the reminders and all its manipulation operations.
 */
public class RemindersActivity extends AppCompatActivity implements IReminders.IView, ReminderAdapterEventListener {

    private IReminders.IPresenter mPresenter;

    private RecyclerView rv_reminders;
    private RemindersRecyclerViewAdapter mRemindersRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private AlertDialog mAlertDialog;
    private NotificationHandler mNotificationHandler;

    /**
     * Configures the SupportActionBar and creates the presenter.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

//        getSupportActionBar().setIcon(R.mipmap.ic_logo_foreground);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setCustomView (R.layout.custom_toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        mPresenter = new RemindersPresenter (this, getApplicationContext());
        mPresenter.onCreate();
    }

    /**
     * Notifies the presenter to be paused.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onPause();
    }

    /**
     * Calls the presenter to be destroyed.
     */
    @Override
    protected void onDestroy () {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    /**
     * Initializes the main components of the activity.
     * @param reminders
     */
    @Override
    public void init(ArrayList<Reminder> reminders) {

        initRemindersAdapter (reminders);
        initFloatingActionButton ();
        initDialogInputReminder ();

        mNotificationHandler = new NotificationHandler (this);
    }

    /**
     * Resets the reminders data of the recycler view and notifies that its data has changed, in
     * order to refresh it.
     * @param reminders The new reminders data.
     */
    @Override
    public void refresh(ArrayList<Reminder> reminders) {
        mRemindersRecyclerViewAdapter.setReminders(reminders);
        mRemindersRecyclerViewAdapter.clearSelectedItem ();
        mRemindersRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * Calls the notification handler to send a notification.
     * @param id The id of the notification.
     * @param text The text to show in the notification.
     */
    @Override
    public void sendNotification(int id, String text) {
        mNotificationHandler.sendNotification (id, text); // Using the position of the reminder in the array list as its notification id
    }

    /**
     * Shows a toast in the string.
     * @param text The text to show in the toast.
     */
    @Override
    public void sendFeedbackToast(String text) {
        Toast.makeText (this, text, Toast.LENGTH_LONG).show();
    }

    /**
     * Callback method that calls the presenter to remove the reminder that matches with the item selected.
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.cm_item_remove)
            mPresenter.onRemoveReminderButtonClick (mRemindersRecyclerViewAdapter.getSelectedItem());

        return super.onContextItemSelected(item);
    }

    /**
     * Initializes the RecyclerAdapter used to handle the list of reminders.
     * Creates the adapter and sets it to the activity.
     * Also register the recycler for context menu events.
     * @param reminders
     */
    void initRemindersAdapter (ArrayList<Reminder> reminders)
    {
        mRemindersRecyclerViewAdapter = new RemindersRecyclerViewAdapter (reminders, this);
        mLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);

        rv_reminders = findViewById(R.id.rv_reminders);
        rv_reminders.setLayoutManager(mLayoutManager);
        rv_reminders.setAdapter(mRemindersRecyclerViewAdapter);

        registerForContextMenu(rv_reminders);

//        rv_reminders.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    /**
     * Initializes the FloatingActionButton by configuring its onClick event.
     */
    void initFloatingActionButton()
    {
        findViewById (R.id.fab_reminders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogInputReminder();
            }
        });
    }

    /**
     * Initializes the AlertDialog that lets the user creates new reminders.
     * Also, configures some views that belongs to its layout.
     */
    void initDialogInputReminder()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder (this);
        dialogBuilder.setCancelable(true);

        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_input_reminder, null);
        dialogBuilder.setView(dialogView);

        final EditText et_note = dialogView.findViewById(R.id.et_input_note);

        et_note.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_note.length() > 0)
                    mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                else
                    mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        });

        dialogBuilder.setNegativeButton(getResources().getString(R.string.dialog_reminder_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        dialogBuilder.setPositiveButton(getResources().getString(R.string.dialog_reminder_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.onAddReminderButtonClick (et_note.getText().toString());
            }
        });

        mAlertDialog = dialogBuilder.create();
        mAlertDialog.setTitle (getResources().getString(R.string.dialog_reminder_title));

        mAlertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                // Min API 23:
//                mAlertDialog.getButton (AlertDialog.BUTTON_POSITIVE).setTextColor (getResources().getColor(R.color.black, null));
//                mAlertDialog.getButton (AlertDialog.BUTTON_NEGATIVE).setTextColor (getResources().getColor(R.color.errorLight, null));

                // Min API 22:
                mAlertDialog.getButton (AlertDialog.BUTTON_POSITIVE).setTextColor (getResources().getColor(R.color.black));
                mAlertDialog.getButton (AlertDialog.BUTTON_NEGATIVE).setTextColor (getResources().getColor(R.color.errorLight));
            }
        });
    }

    /**
     * Shows the AlertDialog used to lets the user creates a new reminder.
     * Its EditText gets cleared of data.
     */
    void showDialogInputReminder ()
    {
        mAlertDialog.show();

        // Clearing the edit text view text data
        EditText et = mAlertDialog.findViewById (R.id.et_input_note);
        if (et != null)
            et.setText("");
    }

    /**
     * Inflates the context menu layout.
     * @param contextMenu
     * @param view
     * @param contextMenuInfo
     */
    @Override
    public void onCreateContextMenuCustom (ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        getMenuInflater().inflate(R.menu.context_reminder, contextMenu);;
    }

    /**
     *
     * @param v
     */
    @Override
    public void onItemCreated (View v) {

    }

    /**
     * Callback method that configures the onClick event for the notification button.
     * @param v The view of the item that is being processed.
     * @param i The item's index.
     */
    @Override
    public void onItemDataFilled (View v, final int i) {

        final Context context = this;

        ImageView im_notification = v.findViewById(R.id.item_iv_notification);

        im_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSendNotificationButtonClick (i);
            }
        });

    }
}
