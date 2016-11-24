package com.seecret.mdb.seecret;

import java.util.ArrayList;

/**
 * Created by kedarthakkar on 11/20/16.
 */

public class Message implements Comparable<Message> {

    private String name;
    private String lastMessage;
    private String time;
    private String tag;

    public Message(String currName, String currLastMessage, String currTime, String tag){
        name = currName;
        lastMessage = currLastMessage;
        time = currTime;
        this.tag = tag;
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

    public int compareTo(Message otherMessage){
        return time.compareTo(otherMessage.getTime());
    }
}
