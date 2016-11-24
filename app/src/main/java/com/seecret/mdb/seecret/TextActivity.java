package com.seecret.mdb.seecret;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {

    static TextAdapter textAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.text_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textAdapter = new TextAdapter(getApplicationContext(), getTexts());
        recyclerView.setAdapter(textAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateTexts();
    }

    public void updateTexts() {
        textAdapter.setTexts(getTexts());
        textAdapter.notifyDataSetChanged();
    }

    public ArrayList<Text> getTexts() {
        ArrayList<Text> texts = new ArrayList<Text>();
        String tag = getIntent().getStringExtra("table name");
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