package com.seecret.mdb.seecret;

import android.content.Context;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.SpannableString;
import android.util.Log;
import android.util.Pair;

import com.mdb.easqlitelib.EaSQLite;

/**
 * Created by Aayush on 11/20/2016.
 */

public class NotificationService extends NotificationListenerService{
    Context context;

    public static final String TITLE_COLUMN = "title";
    public static final String TEXT_COLUMN = "text";

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

        String notificationTag = sbn.getTag();
        notificationTag = "table" + notificationTag.split(":")[1];


        String text = "";
        String title = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Bundle extras = extras = sbn.getNotification().extras;
            text = extras.getCharSequence("android.text").toString();
            title = extras.getCharSequence("android.title").toString();

        }

        Log.i("Package",pack);
        Log.i("Title",title);
        Log.i("Text",text);

        try {
            EaSQLite.initialize(getApplicationContext());
            if (!EaSQLite.getTableNames().contains(notificationTag)) {
                EaSQLite.createTable(notificationTag);
                EaSQLite.addColumn(notificationTag, TITLE_COLUMN, "TEXT");
                EaSQLite.addColumn(notificationTag, TEXT_COLUMN, "TEXT");
            }

            Pair<String, Object> titlePair = new Pair<>(TITLE_COLUMN, (Object) title);
            Pair<String, Object> textPair = new Pair<>(TEXT_COLUMN, (Object) text);
            Pair<String, Object>[] pairs = new Pair[2];
            pairs[0] = titlePair;
            pairs[1] = textPair;
            EaSQLite.addRow(notificationTag, pairs);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification was removed");
    }
}
