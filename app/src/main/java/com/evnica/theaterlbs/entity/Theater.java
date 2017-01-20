package com.evnica.theaterlbs.entity;

/**
 * Created by: Evnica
 * Date: 20.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class Theater {

    private String mTheaterName;
    private String mTheaterDescription;
    private String mTheaterLocation;
    private double  mTheaterLongitude;
    private double mTheaterLatitude;
    private String mThumbImage;
    private String mDetailImage;


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


    public String getTheaterName() {
        return mTheaterName;
    }

    public void setTheaterName(String mTheaterName) {
        this.mTheaterName = mTheaterName;
    }

    public String getTheaterDescription() {
        return mTheaterDescription;
    }

    public void setTheaterDescription(String mTheaterDescription) {
        this.mTheaterDescription = mTheaterDescription;
    }

    public String getTheaterLocation() {
        return mTheaterLocation;
    }

    public void setTheaterLocation(String mTheaterLocation) {
        this.mTheaterLocation = mTheaterLocation;
    }

    public double getTheaterLongitude() {
        return mTheaterLongitude;
    }

    public void setTheaterLongitude(double mTheaterLongitude) {
        this.mTheaterLongitude = mTheaterLongitude;
    }

    public double getTheaterLatitude() {
        return mTheaterLatitude;
    }

    public void setTheaterLatitude(double mTheaterLatitude) {
        this.mTheaterLatitude = mTheaterLatitude;
    }

    public String getThumbImage() {
        return mThumbImage;
    }

    public void setThumbImage(String mThumbImage) {
        this.mThumbImage = mThumbImage;
    }

    public String getDetailImage() {
        return mDetailImage;
    }

    public void setDetailImage(String mDetailImage) {
        this.mDetailImage = mDetailImage;
    }
}
