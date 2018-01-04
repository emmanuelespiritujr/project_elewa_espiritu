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


        String[] timesString = context.getResources().getStringArray(R.array.OakvilleDep);

        ArrayList<TimesDeparture> times = new ArrayList<>();

        //foreach in java
        for (String time: timesString){
            times.add(new TimesDeparture(time));
        }

        return times;
    }
    public static ArrayList<TimesDeparture> getTimesPortCredit(Context context) {


        String[] timesString = context.getResources().getStringArray(R.array.OakvilleDep);

        ArrayList<TimesDeparture> times = new ArrayList<>();

        //foreach in java
        for (String time: timesString){
            times.add(new TimesDeparture(time));
        }

        return times;
    }
    public static ArrayList<TimesDeparture> getTimesClarkson(Context context) {


        String[] timesString = context.getResources().getStringArray(R.array.OakvilleDep);

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
