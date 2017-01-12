package com.seecret.mdb.seecret;

import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.util.Log;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by kedarthakkar on 11/20/16.
 */

public class Message implements Comparable<Message> {

    private String name;
    private String lastMessage;
    public String time;
    private String tag;
    private Bitmap b;
    private int count;

    public Message(String currName, String currLastMessage, String currTime, String tag, Bitmap b, String count){
        name = currName;
        lastMessage = currLastMessage;
        time = currTime;
        this.tag = tag;
        this.b = b;
        this.count = Integer.parseInt(count);
    }

    public String getName(){
        return name;
    }

    public String getLastMessage(){
        return lastMessage;
    }

    public String getTime(){
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a MM/dd");
        String time = formatter.format(date);
        return time;
    }

    public String getTag(){
        return tag;
    }

    public Bitmap getBitmap() { return b; }

    public int getCount() { return count; }

    public int compareTo(Message otherMessage) {
        return time.compareTo(otherMessage.time);
    }
}