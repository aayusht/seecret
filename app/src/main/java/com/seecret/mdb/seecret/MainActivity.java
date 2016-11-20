package com.seecret.mdb.seecret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Message> messageList = new ArrayList<Message>();

        ArrayList<String> fakeNames = new ArrayList<String>();
        fakeNames.add("Leon Kwak");

        Message fakeMessage1 = new Message(fakeNames, "You: you up lol", "2:12 am", "fakeurl.com");

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
        recyclerView.setAdapter(messageAdapter);
    }
}
