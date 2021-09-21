package com.example.wikings.Pasandi;

import java.io.Serializable;

public class SavedPlace implements Serializable {

    private String name;
    private String RouteNumber;


    public SavedPlace(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteNumber() {
        return RouteNumber;
    }

    public void setRouteNumber(String routeNumber) {
        RouteNumber = routeNumber;
    }
}
