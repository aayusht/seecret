package com.seecret.mdb.seecret;

/**
 * Created by leonk7 on 11/20/16.
 */

public class Text {

    String message;
    String timestamp;

    public Text(String aMessage, String aTimestamp){
        message = aMessage;
        timestamp = aTimestamp;
    }

    public String getMessage(){
        return message;
    }

    public String getTimestamp(){
        return timestamp;
    }
}
