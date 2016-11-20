package com.seecret.mdb.seecret;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
>>>>>>> be607ea9d69da6ec43c3a824ff26d465d137487c

public class MainActivity extends AppCompatActivity {

    TextAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Message> messageList = new ArrayList<Message>();

        Message fakeMessage1 = new Message("Leon Kwak", "You: you up lol haha", "2:12 am", "https://d3jc3ahdjad7x7.cloudfront.net/lJCJA7nyXjhz3q5jMD2WaL5TCwgHGStXvmzmS7hiTyGaJFdR.jpg");

        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);
        messageList.add(fakeMessage1);

        messageAdapter = new MessageAdapter(getApplicationContext(), messageList);
<<<<<<< HEAD
        recyclerView.setAdapter(messageAdapter);*/
=======
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
>>>>>>> be607ea9d69da6ec43c3a824ff26d465d137487c
    }
}
