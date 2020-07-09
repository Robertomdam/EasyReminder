package com.rmm.easyreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class RemindersActivity extends AppCompatActivity {

    ArrayList<Reminder> mReminders;

    RecyclerView rv_reminders;
    RemindersRecyclerViewAdapter mRemindersRecyclerViewAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        mReminders = new ArrayList<Reminder>();

        // Three reminders are created at the begining for testing purposes.
        for (int i = 0; i < 18; i++) {
            mReminders.add( new Reminder("Test rem " + i) );
        }

        mReminders.add( new Reminder("Test Other") );

        mRemindersRecyclerViewAdapter = new RemindersRecyclerViewAdapter (mReminders);
        mLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);

        rv_reminders = findViewById(R.id.rv_reminders);
        rv_reminders.setLayoutManager(mLayoutManager);
        rv_reminders.setAdapter(mRemindersRecyclerViewAdapter);

//        rv_reminders.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
