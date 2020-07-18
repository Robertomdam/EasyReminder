package com.rmm.easyreminder;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import org.junit.Assert;

import com.rmm.easyreminder.database.Database;

@RunWith(AndroidJUnit4.class)
public class ReminderManagerInstrumentedTest {

    private Context mContext;
    private Database mDatabase;

    @Before
    public void init ()
    {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        mDatabase = new Database ( mContext );
    }

    @Test
    public void checkDatabase ()
    {

        Log.d("DEBUGGING", "(TEST) Database num reminders: " + mDatabase.retrieveReminders().size());
        Assert.assertTrue ( mDatabase.retrieveReminders().size() > 0 );
    }

}