package com.rmm.easyreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RemindersActivity extends AppCompatActivity implements ReminderAdapterEventListener {

    ArrayList<Reminder> mReminders;

    RecyclerView rv_reminders;
    RemindersRecyclerViewAdapter mRemindersRecyclerViewAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        initRemindersData();
        initRemindersAdapter();
        initFloatingActionButton();
        initDialogInputReminder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.cm_item_remove)
//            Log.d("DEBUGGING", "item to remove: " + mRemindersRecyclerViewAdapter.getSelectedItem());
            removeReminder(mRemindersRecyclerViewAdapter.getSelectedItem());
        else
            Log.d("DEBUGGING", "other thing");

        return super.onContextItemSelected(item);
    }

    void initRemindersData ()
    {
        mReminders = new ArrayList<Reminder>();

        // Three reminders are created at the begining for testing purposes.
        for (int i = 0; i < 18; i++) {
            mReminders.add( new Reminder("Test rem " + i) );
        }

        mReminders.add( new Reminder("Test Other") );
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

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        dialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText et = dialogView.findViewById(R.id.et_input_note);
                addReminder(et.getText().toString());
            }
        });

        mAlertDialog = dialogBuilder.create();
        mAlertDialog.setTitle ("Add reminder");
    }

    void refreshData ()
    {
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
        mReminders.add (new Reminder(note));
        refreshData();

        Toast.makeText(this, "New reminded was added successfully", Toast.LENGTH_LONG).show();
    }

    void removeReminder (int i)
    {
        mReminders.remove(i);
        refreshData();

        Toast.makeText(this, "Reminder removed successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenuCustom (ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        getMenuInflater().inflate(R.menu.context_reminder, contextMenu);;
    }

    @Override
    public void onItemCreated (View v) {

    }

    @Override
    public void onItemDataFilled (int i) {
    }
}
