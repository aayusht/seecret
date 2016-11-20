package com.seecret.mdb.seecret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        recyclerView.setAdapter(messageAdapter);*/
    }
}
