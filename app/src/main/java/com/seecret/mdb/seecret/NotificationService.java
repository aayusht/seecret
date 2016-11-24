package com.seecret.mdb.seecret;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        String pack = sbn.getPackageName();
        Log.i("Tag", "" + sbn.getTag());
        Log.i("time", "" + sbn.getPostTime());

        final String notificationTag = parseTag(sbn.getTag());
        Date date = new Date(sbn.getPostTime());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String time = formatter.format(date);

        if (notificationTag != null && sbn.getPackageName().equals("com.facebook.orca")) {

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
                ContentValues contentValues = new ContentValues();
                contentValues.put(TITLE_COLUMN, title);
                contentValues.put(TEXT_COLUMN, text);
                contentValues.put(TIME_COLUMN, time);
                database.insert(notificationTag, null, contentValues);
                Log.i("pls", database.toString());


                String[] projection = {"id", TITLE_COLUMN, TEXT_COLUMN, TIME_COLUMN};

                Cursor cursor = database.query(notificationTag, projection, null, null, null, null, null);
                updateTable(notificationTag, false);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification was removed " + sbn.getTag());
        boolean deleted = deleteDatabase(parseTag(sbn.getTag()));
        if (!deleted) {Log.e("THATS NO GOOOOD", "sad");}
        updateTable(parseTag(sbn.getTag()), true);
    }

    public String parseTag(String tag) {
        return "table" + tag.split(":")[1].replaceAll("[^a-zA-Z0-9]", "");
    }

    private void updateTable(final String tag, final boolean clearing) {
        Handler mainHandler = new Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                MainActivity.messageAdapter.setMessages(getMessages());
                MainActivity.messageAdapter.notifyDataSetChanged();
                if (!clearing) {
                    TextActivity.textAdapter.setTexts(getTexts(tag));
                    TextActivity.textAdapter.notifyDataSetChanged();
                }
                else {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        };
        mainHandler.post(myRunnable);
    }

    private ArrayList<Message> getMessages() {
        ArrayList<Message> messages = new ArrayList<Message>();
        String[] tags = databaseList();
        for (String s : tags) { Log.i("tag ", s); }
        for (int i = 0; i < tags.length; ++i) {
            String tag = tags[i].replaceAll("[^a-zA-Z0-9]", "");
            if (!tag.contains("journal")) {
                Log.i("requested from: ", tag);
                CommentsDatabaseHelper helper = new CommentsDatabaseHelper(getApplicationContext(), tag);
                SQLiteDatabase database = helper.getWritableDatabase();

                String[] projection = {"id", "title", "text", "time"};

                Cursor cursor = database.query(tag, projection, null, null, null, null, null);
                cursor.moveToLast();
                Log.i("cursor", "" + cursor.getString(1));
                messages.add(new Message(cursor.getString(1), cursor.getString(2), cursor.getString(3), tag));
            }
        }
        return messages;
    }

    public ArrayList<Text> getTexts(String tag) {
        ArrayList<Text> texts = new ArrayList<Text>();
        CommentsDatabaseHelper helper = new CommentsDatabaseHelper(getApplicationContext(), tag);
        SQLiteDatabase database = helper.getWritableDatabase();

        String[] projection = {"id", "title", "text", "time"};

        Cursor cursor = database.query(tag, projection, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            texts.add(new Text(cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        return texts;
    }
}


