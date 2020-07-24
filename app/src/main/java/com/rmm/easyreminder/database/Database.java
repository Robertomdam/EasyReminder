package com.rmm.easyreminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rmm.easyreminder.data.Reminder;

import java.util.ArrayList;

/**
 * @author Roberto
 * This class will handle the connexion with the SQLite database.
 */
public class Database extends SQLiteOpenHelper {

    public Database (@Nullable Context context) {
        super (context, DB_Const.SQL_DATABASE_NAME, null, DB_Const.SQL_DATABASE_VERSION);
    }

    /**
     * Executes the query that creates the database.
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_Const.SQL_QUERY_CREATE_TABLE_REMINDERS);
    }

    /**
     * Updates the database by simply removing the current one and creating a new one.
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DB_Const.SQL_QUERY_DROP_TABLE_REMINDERS);
        onCreate(sqLiteDatabase);
    }

    /**
     * Inserts a reminder into the database.
     * @param reminder The reminder that has to be added.
     */
    public void insert (Reminder reminder)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put (DB_Const.SQL_COL_NAME_NOTE, reminder.getNote());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DB_Const.SQL_TABLE_NAME_REMINDERS, null, contentValues);
    }

    /**
     * Removes a reminder from the database.
     * @param reminder The reminder to be removed.
     */
    public void remove (Reminder reminder)
    {
        remove (reminder.getId());
    }

    /**
     * Removes a reminder from the database.
     * @param id The id of the reminder to be removed.
     */
    public void remove (int id)
    {
        String [] args = { String.valueOf (id) };

        SQLiteDatabase db = getWritableDatabase();
        db.delete (DB_Const.SQL_TABLE_NAME_REMINDERS, DB_Const.SQL_COL_NAME_ID + " = ? ", args);
    }

    /**
     * Retrieves all the reminders in the database.
     * @return ArrayList that contains all the reminders in the database.
     */
    public ArrayList<Reminder> retrieveReminders ()
    {
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();

        SQLiteDatabase db = getReadableDatabase ();

        Cursor cursor = db.query (
                DB_Const.SQL_TABLE_NAME_REMINDERS,
                null,
                null, null,
                null, null,
                null
        );

        while (cursor.moveToNext())
        {
            int      id = cursor.getInt    ( cursor.getColumnIndexOrThrow (DB_Const.SQL_COL_NAME_ID  ) );
            String note = cursor.getString ( cursor.getColumnIndexOrThrow (DB_Const.SQL_COL_NAME_NOTE) );

            reminders.add( new Reminder ( id, note ) );
        };

        cursor.close();

        return reminders;
    }

    /**
     * Retrieves a single reminder based on its id.
     * @param id The id of the reminder.
     * @return The reminder that matches with the id.
     */
    public Reminder getReminder (int id)
    {
        String [] args = { String.valueOf (id) };

        Reminder reminder = null;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query (
                DB_Const.SQL_TABLE_NAME_REMINDERS,
                null,
                DB_Const.SQL_COL_NAME_ID + " LIKE ? ", args,
                null, null,
                null
        );

        while (cursor.moveToNext())
        {
            int      _id = cursor.getInt    ( cursor.getColumnIndexOrThrow (DB_Const.SQL_COL_NAME_ID  ) );
            String _note = cursor.getString ( cursor.getColumnIndexOrThrow (DB_Const.SQL_COL_NAME_NOTE) );

            reminder =  new Reminder ( _id, _note );
        };

        cursor.close();

        return reminder;
    }

    /**
     * Debugging purpose method. Shows all the reminders of the db in the log console.
     */
    private void debugTable ()
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query (
                DB_Const.SQL_TABLE_NAME_REMINDERS,
                null,
                null, null,
                null, null,
                null
        );

        while (cursor.moveToNext())
        {
            int      _id = cursor.getInt    ( cursor.getColumnIndexOrThrow (DB_Const.SQL_COL_NAME_ID  ) );
            String _note = cursor.getString ( cursor.getColumnIndexOrThrow (DB_Const.SQL_COL_NAME_NOTE) );

            Log.d("DEBUGGING", "rem: " + _id + " - " + _note);
        };

        cursor.close();
    }
}
