package com.seecret.mdb.seecret;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.seecret.mdb.seecret.NotificationService;
import java.util.ArrayList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Message> messages = new ArrayList<Message>();
        String[] tags = databaseList();
        for (int i = 0; i < tags.length; ++i) {
            String tag = tags[i].replaceAll("[^a-zA-Z0-9]", "");
            if (!tag.contains("journal")) {
                Log.i("requested from: ", tag);
                CommentsDatabaseHelper helper = new CommentsDatabaseHelper(getApplicationContext(), tag);
                SQLiteDatabase database = helper.getWritableDatabase();

                String[] projection = {"id", "title", "text"};

                Cursor cursor = database.query(tag, projection, null, null, null, null, null);
                cursor.moveToLast();
                messages.add(new Message(cursor.getString(1), cursor.getString(2), tag, "idek"));
            }
        }

        messageAdapter = new MessageAdapter(getApplicationContext(), messages);
        recyclerView.setAdapter(messageAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("Permissions Needed", true)){
            editor.putBoolean("Permissions Needed", false);
            editor.apply();
            Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch(item.getItemId()){

            case R.id.messenger:
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.facebook.orca");
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}