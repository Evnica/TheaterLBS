package com.evnica.theaterlbs.model;

/**
 * Created by: Evnica
 * Date: 20.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class Theater {

    private String mName;
    private String mDescription;
    private String mAddress;
    private double mLongitude;
    private double mLatitude;
    private String mThumbImageLink;
    private String mDetailImageLink;


    //----------------------------------- Constructor ----------------------------------------------
    public Theater(){}
    public Theater(String theaterName, String theaterLocation,
                   double theaterLongitude, double theaterLatitude) {
        mName = theaterName;
        mAddress = theaterLocation;
        mLongitude = theaterLongitude;
        mLatitude = theaterLatitude;
    }

    public String[] toStringArray()
    {
        return new String[]{mName, mAddress, mDescription,
                            mLatitude + "", mLongitude + "",
                            mThumbImageLink, mDetailImageLink};
    }

    //---------------------------------- Getters and Setters ------------------------------------


    public String getName() {
        return mName;
    }

    public void setName(String mTheaterName) {
        this.mName = mTheaterName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mTheaterDescription) {
        this.mDescription = mTheaterDescription;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mTheaterLocation) {
        this.mAddress = mTheaterLocation;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mTheaterLongitude) {
        this.mLongitude = mTheaterLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mTheaterLatitude) {
        this.mLatitude = mTheaterLatitude;
    }

    public String getThumbImageLink() {
        return mThumbImageLink;
    }

    public void setThumbImageLink(String mThumbImage) {
        this.mThumbImageLink = mThumbImage;
    }

    public String getDetailImageLink() {
        return mDetailImageLink;
    }

    public void setDetailImageLink(String mDetailImage) {
        this.mDetailImageLink = mDetailImage;
    }
}
