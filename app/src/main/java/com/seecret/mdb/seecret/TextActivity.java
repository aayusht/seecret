package com.seecret.mdb.seecret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {

    TextAdapter textAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.text_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Text> texts = new ArrayList<Text>();
        Text text1 = new Text("Victor is hot i cannot lie", "04:20");
        texts.add(text1);

        textAdapter = new TextAdapter(getApplicationContext(), texts);
        recyclerView.setAdapter(textAdapter);
    }
}