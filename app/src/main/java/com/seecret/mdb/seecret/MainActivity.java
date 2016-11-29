package com.seecret.mdb.seecret;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;

import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Conversations");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        messageAdapter = new MessageAdapter(getApplicationContext(), getMessages());
        recyclerView.setAdapter(messageAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("Permissions Needed", true)){
            editor.putBoolean("Permissions Needed", false);
            editor.apply();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            LinearLayout layout = new LinearLayout(builder.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            final TextView warningMessage = new TextView(builder.getContext());
            warningMessage.setText("Seecret lets you view your Facebook messages secretly. To do this, you must give Seecret access to your notifications. Continue?");
            warningMessage.setPadding(64, 24, 64, 0);
            layout.addView(warningMessage);

            builder.setView(layout);

            builder.setTitle("Welcome to Seecret!")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMessages();
    }

    public void updateMessages() {
        try {
            messageAdapter.setMessages(getMessages());
        }
        catch (Exception e) {

        }
        messageAdapter.notifyDataSetChanged();
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

                String[] projection = {"id", "title", "text", "time", "icon"};

                Cursor cursor = database.query(tag, projection, null, null, null, null, null);
                cursor.moveToLast();
                Log.i("cursor", "" + cursor.getString(1));
                byte[] bitmapdata = cursor.getBlob(4);
                Bitmap b = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);;
                messages.add(new Message(cursor.getString(1), cursor.getString(2), cursor.getString(3), tag, b));
            }
        }
        Collections.sort(messages);
        Collections.reverse(messages);
        return messages;
    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){

        switch(item.getItemId()){

            case R.id.messenger:

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.facebook.orca");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                return true;

            case R.id.trash:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);

                builder1.setTitle("Are you sure you want to delete all conversations?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] tags = databaseList();
                                for (int i = 0; i < tags.length; ++i) {
                                    String tag = tags[i].replaceAll("[^a-zA-Z0-9]", "");
                                    if (!tag.contains("journal")) {
                                        deleteDatabase(tag);
                                    }
                                }
                                updateMessages();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }
}
