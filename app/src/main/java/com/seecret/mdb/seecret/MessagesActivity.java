package com.seecret.mdb.seecret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity {

    MessageAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.song_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Message> messages = new ArrayList<Message>();
        Message msg1 = new Message("Victor is hot i cannot lie", "04:20");
        messages.add(msg1);

        msgAdapter = new MessageAdapter(getApplicationContext(), messages);
        recyclerView.setAdapter(msgAdapter);
    }
}