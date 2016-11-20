package com.seecret.mdb.seecret;

import java.util.ArrayList;

/**
 * Created by kedarthakkar on 11/20/16.
 */

public class Message implements Comparable<Message> {

    public ArrayList<String> names;
    public String lastMessage;
    public String time;
    public String imageUrl;

    public Message(ArrayList<String> currNames, String currLastMessage, String currTime, String currImageUrl){
        names = currNames;
        lastMessage = currLastMessage;
        time = currTime;
        imageUrl = currImageUrl;
    }

    public ArrayList<String> getNames(){
        return names;
    }

    public String getLastMessage(){
        return lastMessage;
    }

    public String getTime(){
        return time;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public int compareTo(Message otherMessage){
        return time.compareTo(otherMessage.getTime());
    }
}
