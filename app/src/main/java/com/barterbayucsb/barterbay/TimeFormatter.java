package com.barterbayucsb.barterbay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Daniel on 2/21/2017.
 */

//use this to get the correct time format when displaying post age (and potentially message/comment age in the future)
//timestamps are taken in in the form yyyy_MM_dd_HH_mm_ss. string length for timestamp is 19 (18 elements).

public class TimeFormatter {

    private int second;
    private  int minute;
    private  int hour;
    private  int day;
    private  int month;
    private  int year;
    private int[] secondsInMonths = new int[]{2678400,2419200,2505600,2678400,2592000,2678400,2592000,2678400,2678400,2592000,2678400,2592000,2678400};


    public TimeFormatter()
    {

    }
    public TimeFormatter(String timestamp)
    {
        year = Integer.parseInt(timestamp.substring(0,4));
        month = Integer.parseInt(timestamp.substring(5,7));
        day = Integer.parseInt(timestamp.substring(8,10));
        hour = Integer.parseInt(timestamp.substring(11,13));
        minute = Integer.parseInt(timestamp.substring(14,16));
        second = Integer.parseInt(timestamp.substring(17,19));


    }
    public static int compareAges(Offer O1, Offer O2){
        TimeFormatter T1 = new TimeFormatter(O1.id);
        TimeFormatter T2 = new TimeFormatter(O2.id);
        if(SettingsActivity.Preferences.getFILTER_LOW_TO_HIGH())
        return Integer.compare(T1.getDiff(),T2.getDiff());
        else return Integer.compare(T2.getDiff(), T1.getDiff());
    }

    protected String formattedAge(String timestamp)
    {
        if(timestamp.equals("test id")) return "debug";
        int units = 0;
        String unit = "";
        getDateInfo(timestamp);
        getDiff();
        int difference = getDiff();
        if(difference < 5184000) {
            if (difference < 60) {
                return("Posted less than a minute ago.");
            } else if (difference < 3600) {
                if (difference < 119) unit = "minute";
                else unit = "minutes";
                units = difference / 60;
            } else if (difference < 86400) {
                if (difference < 7199) unit = "hour";
                else unit = "hours";
                units = difference / 3600;
            }

            //else if(difference < 5184000){
            else {
                if (difference < 172799) unit = "day";
                else unit = "days";
                units = difference / 86400;
            }
            return ("Posted " + units + " " + unit + " "  + "ago.");

        }
        else
        return ("Posted on" + month + "/" + day + "/" + year);
    }

    protected int getDiff() //returns the difference in seconds
    {
        int sum = second + minute * 60 + hour * 3600 + day * 86400 * year * 31558150 + secondsInMonths(month);
        TimeFormatter CurrentTF =  new TimeFormatter(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US).format(new Date()));
        int currentSum = CurrentTF.minute * 60 + CurrentTF.hour * 3600 + CurrentTF.day * 86400 * CurrentTF.year * 31558150 + secondsInMonths(CurrentTF.month);


        return currentSum-sum + 20; //there's a roughly 20 second desync for some reason. //TODO: possibly figure out why it's desynced but it's pretty much fine how it is.
    }





    protected int secondsInMonths(int month)
    {
        int sum = 0;
        for(int i = month; i > 0; i--)
        {
            sum += secondsInMonths[i];
        }
        return sum;
    }
    protected void getDateInfo(String timestamp) //sets the int fields.
    {
        year = Integer.parseInt(timestamp.substring(0,4));
        month = Integer.parseInt(timestamp.substring(5,7));
        day = Integer.parseInt(timestamp.substring(8,10));
        hour = Integer.parseInt(timestamp.substring(11,13));
        minute = Integer.parseInt(timestamp.substring(14,16));
        second = Integer.parseInt(timestamp.substring(17,19));
    }

}
