package com.example.stanley.alarm;


import java.io.Serializable;

/**
 * Created by Stanley on 10/25/2015.
 */
public class Alarm implements Serializable {
     public boolean enable;
     public int start_hour;
     public int start_min;
     public int end_hour;
     public int end_min;
     public String ringtone;
     public int topic;
     public boolean weather = false;
     public boolean calendar = false;
}
