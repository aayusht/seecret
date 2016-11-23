package com.seecret.mdb.seecret;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.SpannableString;
import android.util.Log;
import android.util.Pair;

//import com.mdb.easqlitelib.EaSQLite;

/**
 * Created by Aayush on 11/20/2016.
 */

public class NotificationService extends NotificationListenerService{
    Context context;

    public static final String TITLE_COLUMN = "title";
    public static final String TEXT_COLUMN = "text";
    public static final String TIME_COLUMN = "time";

    @Override
    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();

    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        //Log.i("screw you", "screw you");
        String pack = sbn.getPackageName();
        Log.i("Tag", "" + sbn.getTag());
        Log.i("time", "" + sbn.getPostTime());

        String notificationTag = sbn.getTag();

        notificationTag.replaceAll("[^a-zA-Z0-9]", "");
        if (notificationTag != null && sbn.getPackageName().equals("com.facebook.orca")) {
            notificationTag = "table" + notificationTag.split(":")[1];

            String text = "";
            String title = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                Bundle extras = extras = sbn.getNotification().extras;
                text = extras.getCharSequence("android.text").toString();
                title = extras.getCharSequence("android.title").toString();

            }

            Log.i("Package", pack);
            Log.i("Title", title);
            Log.i("Text", text);

            try {
                CommentsDatabaseHelper helper = new CommentsDatabaseHelper(getApplicationContext(), notificationTag);
                SQLiteDatabase database = helper.getWritableDatabase();
            /*
            //start sql
            EaSQLite.initialize(getApplicationContext());
            //if the tag isn't one of the names of the tables, make a new one and add the columns
            if (!EaSQLite.getTableNames().contains(notificationTag)) {
                EaSQLite.createTable(notificationTag);
                EaSQLite.addColumn(notificationTag, TITLE_COLUMN, "TEXT");
                EaSQLite.addColumn(notificationTag, TEXT_COLUMN, "TEXT");
            }

            //add the items in the columns using Pair objects
            Pair<String, Object> titlePair = new Pair<>(TITLE_COLUMN, (Object) title);
            Pair<String, Object> textPair = new Pair<>(TEXT_COLUMN, (Object) text);
            Pair<String, Object>[] pairs = new Pair[2];
            pairs[0] = titlePair;
            pairs[1] = textPair;
            EaSQLite.addRow(notificationTag, pairs);
            */
                ContentValues contentValues = new ContentValues();
                contentValues.put(TITLE_COLUMN, title);
                contentValues.put(TEXT_COLUMN, text);
                database.insert(notificationTag, null, contentValues);
                Log.i("pls", database.toString());


                String[] projection = {"id", TITLE_COLUMN, TEXT_COLUMN};

        /*Queries in a database return a Cursor object. We pass in the cursor as a parameter to the adapter, and then
        * set the adapter to the recyclerView*/
                Cursor cursor = database.query(notificationTag, projection, null, null, null, null, null);
                //cursor.moveToLast();
                //Log.i("info", "" + cursor.getInt(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
                //MainActivity.updateMessageAdapter(cursor);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification was removed");
    }


}


