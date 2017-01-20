package com.evnica.theaterlbs.entity;

/**
 * Created by: Evnica
 * Date: 20.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class Theater {

    private String mTheaterName;
    private String mTheaterLocation;
    private double  mTheaterLongitude;
    private double mTheaterLatitude;


    //----------------------------------- Constructor ----------------------------------------------
    public Theater(){}
    public Theater(String theaterName, String theaterLocation,
                   double theaterLongitude, double theaterLatitude) {
        mTheaterName = theaterName;
        mTheaterLocation = theaterLocation;
        mTheaterLongitude = theaterLongitude;
        mTheaterLatitude = theaterLatitude;
    }

    //---------------------------------- Getters and Setters ------------------------------------
    public String getmTheaterName() {
        return mTheaterName;
    }
    public String getmTheaterLocation() {
        return mTheaterLocation;
    }
    public double getmTheaterLongitude() {
        return mTheaterLongitude;
    }
    public double getmTheaterLatitude() {
        return mTheaterLatitude;
    }

}
