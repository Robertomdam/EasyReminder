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

public class RemindersActivity extends AppCompatActivity implements ReminderAdapterEventListener {

    ArrayList<Reminder> mReminders;
    ReminderManager mReminderManager;

    RecyclerView rv_reminders;
    RemindersRecyclerViewAdapter mRemindersRecyclerViewAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    AlertDialog mAlertDialog;

    NotificationHandler mNotificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        initRemindersData();
        initRemindersAdapter();
        initFloatingActionButton();
        initDialogInputReminder();

        mNotificationHandler = new NotificationHandler (this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void onDestroy () {
        mReminderManager.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.cm_item_remove)
            removeReminder(mRemindersRecyclerViewAdapter.getSelectedItem());

        return super.onContextItemSelected(item);
    }

    void initRemindersData ()
    {
        mReminderManager = new ReminderManager (getApplicationContext());
        mReminders = mReminderManager.getReminders();
    }
    void initRemindersAdapter ()
    {
        mRemindersRecyclerViewAdapter = new RemindersRecyclerViewAdapter (mReminders, this);
        mLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);

        rv_reminders = findViewById(R.id.rv_reminders);
        rv_reminders.setLayoutManager(mLayoutManager);
        rv_reminders.setAdapter(mRemindersRecyclerViewAdapter);

        registerForContextMenu(rv_reminders);

//        rv_reminders.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    void initFloatingActionButton()
    {
        findViewById (R.id.fab_reminders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogInputReminder();
            }
        });
    }

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

                if (et_note.length() > 0)
                    addReminder(et_note.getText().toString());
            }
        });

        mAlertDialog = dialogBuilder.create();
        mAlertDialog.setTitle (getResources().getString(R.string.dialog_reminder_title));
    }

    void refreshData ()
    {
        mReminders = mReminderManager.getReminders();

        mRemindersRecyclerViewAdapter.setReminders(mReminders);
        mRemindersRecyclerViewAdapter.clearSelectedItem ();
        mRemindersRecyclerViewAdapter.notifyDataSetChanged();
    }

    void showDialogInputReminder ()
    {
        mAlertDialog.show();

        // Clearing the edit text view text data
        EditText et = mAlertDialog.findViewById (R.id.et_input_note);
        if (et != null)
            et.setText("");
    }

    void addReminder (String note)
    {
        mReminderManager.add (new Reminder (note));
        refreshData();

        Toast.makeText(this, getResources().getString(R.string.add_reminder), Toast.LENGTH_LONG).show();
    }

    void removeReminder (int itemIndex)
    {
        mReminderManager.remove (mReminders.get(itemIndex).getId());
        refreshData();

        Toast.makeText(this, getResources().getString(R.string.remove_reminder), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenuCustom (ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        getMenuInflater().inflate(R.menu.context_reminder, contextMenu);;
    }

    @Override
    public void onItemCreated (View v) {

    }

    @Override
    public void onItemDataFilled (View v, final int i) {

        final Context context = this;

        ImageView im_notification = v.findViewById(R.id.item_iv_notification);

        im_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotificationHandler.sendNotification (i, mReminders.get(i).getNote()); // Using the position of the reminder in the array list as its notification id
            }
        });

    }
}
