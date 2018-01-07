package com.example.jespiritu.project_elewa_espiritu;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by jespiritu on 2018-01-03.
 */

public class Route {
    private String name;

    public Route(String name)
    {
        this.name = name;
    }

    public static ArrayList<Route> getRoutes(Context context) {

        //the list of strings of routes the user can take
        String[] routesString = context.getResources().getStringArray(R.array.routes);

        ArrayList<Route> routes = new ArrayList<>();

        //foreach in java
        for (String route: routesString){
            routes.add(new Route(route));
        }

        return routes;
    }

    //override - similar to c#
    public String toString(){
        return name;
    }
}

