package com.example.jespiritu.project_elewa_espiritu;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by salimelewa on 2018-01-04.
 */

public class TimesDeparture {
    private String name;

    public TimesDeparture(String name)
    {
        this.name = name;
    }

    public static ArrayList<TimesDeparture> getTimesOakville(Context context) {


        //the list of strings of departure times
        String[] timesString = context.getResources().getStringArray(R.array.OakvilleDepE);

        ArrayList<TimesDeparture> times = new ArrayList<>();

        //foreach in java
        for (String time: timesString){
            times.add(new TimesDeparture(time));
        }

        return times;
    }

    public static ArrayList<TimesDeparture> getTimesClarksonE(Context context) {


        String[] timesString = context.getResources().getStringArray(R.array.ClarksonDepE);

        ArrayList<TimesDeparture> times = new ArrayList<>();

        //foreach in java
        for (String time: timesString){
            times.add(new TimesDeparture(time));
        }

        return times;
    }

    public static ArrayList<TimesDeparture> getTimesClarksonW(Context context) {


        String[] timesString = context.getResources().getStringArray(R.array.ClarksonDepW);

        ArrayList<TimesDeparture> times = new ArrayList<>();

        //foreach in java
        for (String time: timesString){
            times.add(new TimesDeparture(time));
        }

        return times;
    }

    public static ArrayList<TimesDeparture> getTimesPortCredit(Context context) {


        String[] timesString = context.getResources().getStringArray(R.array.PortCreditDepW);

        ArrayList<TimesDeparture> times = new ArrayList<>();

        //foreach in java
        for (String time: timesString){
            times.add(new TimesDeparture(time));
        }

        return times;
    }

    //override - similar to c#
    public String toString(){
        return name;
    }
}
