package com.seecret.mdb.seecret;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        setContentView(R.layout.activity_text);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.text_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Text> texts = new ArrayList<Text>();
        String tag = getIntent().getStringExtra("table name");

        CommentsDatabaseHelper helper = new CommentsDatabaseHelper(getApplicationContext(), tag);
        SQLiteDatabase database = helper.getWritableDatabase();

        String[] projection = {"id", "title", "text"};

        Cursor cursor = database.query(tag, projection, null, null, null, null, null);
        cursor.moveToLast();
        while (!cursor.isBeforeFirst()) {
            texts.add(new Text(cursor.getString(2), cursor.getString(1)));
            cursor.moveToPosition(cursor.getPosition() - 1);
        }

        textAdapter = new TextAdapter(getApplicationContext(), texts);
        recyclerView.setAdapter(textAdapter);
    }
}