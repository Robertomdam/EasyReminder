package com.rmm.easyreminder.database;

public class DB_Const {

    static final int    SQL_DATABASE_VERSION = 1;
    static final String SQL_DATABASE_NAME = "DB_Reminders.db";

    static final String SQL_TABLE_NAME_REMINDERS = "reminders";
    static final String SQL_COL_NAME_ID = "id";
    static final String SQL_COL_NAME_NOTE = "note";

    static final String SQL_QUERY_CREATE_TABLE_REMINDERS =
            "CREATE TABLE " + SQL_TABLE_NAME_REMINDERS + " ("
                    + SQL_COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SQL_COL_NAME_NOTE + " TEXT)";

    static final String SQL_QUERY_DROP_TABLE_REMINDERS = "DROP TABLE" + SQL_TABLE_NAME_REMINDERS;

}
