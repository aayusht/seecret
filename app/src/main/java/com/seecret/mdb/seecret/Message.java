package com.seecret.mdb.seecret;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by kedarthakkar on 11/20/16.
 */

public class Message implements Comparable<Message> {

    private String name;
    private String lastMessage;
    private String time;
    private String tag;
    private Bitmap b;

    public Message(String currName, String currLastMessage, String currTime, String tag, Bitmap b){
        name = currName;
        lastMessage = currLastMessage;
        time = currTime;
        this.tag = tag;
        this.b = b;
    }

    public String getName(){
        return name;
    }

    public String getLastMessage(){
        return lastMessage;
    }

    public String getTime(){
        return time;
    }

    public String getTag(){
        return tag;
    }

    public Bitmap getBitmap() { return b; }

    public int compareTo(Message otherMessage){
        return time.compareTo(otherMessage.getTime());
    }
}