package com.iiitd.ucsf.models;

import java.sql.Timestamp;

public class AudioData {
    private String name;
    private String count;
    private Timestamp myTime;

    AudioData(String name,String count, Timestamp timestamp) {
        this.name = name;
        this.myTime = timestamp;
        this.count=count;
    }

    // getters and setters of your choosing
}