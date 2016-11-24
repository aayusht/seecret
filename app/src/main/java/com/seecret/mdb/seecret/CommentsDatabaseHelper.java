package com.seecret.mdb.seecret;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by innologic2 on 11/22/16.
 */
public class CommentsDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    private final String TEXT_TYPE = " TEXT";
    private final String COMMA_SEP = ", ";

    private String tableName;

    private String getEntries() {
        Log.i("made new table with", tableName);
        return "CREATE TABLE " + tableName + " (" +
                "id" + " INTEGER PRIMARY KEY" + COMMA_SEP +
                "title" + TEXT_TYPE + COMMA_SEP +
                "text" + TEXT_TYPE + COMMA_SEP +
                "time" + TEXT_TYPE +
                " )";
    }

    public CommentsDatabaseHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
        tableName = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getEntries());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade is not implemented in this demo
    }
}
