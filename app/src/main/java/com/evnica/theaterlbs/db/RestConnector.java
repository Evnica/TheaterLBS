package com.evnica.theaterlbs.db;

import com.evnica.theaterlbs.entity.Theater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by: Evnica
 * Date: 20.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class RestConnector
{

    private static RestConnector sInstance;

    public static RestConnector getsInstance()
    {
        if(sInstance == null)
        {
            sInstance = new RestConnector();
        }
        return sInstance;
    }

    public ArrayList<Theater> getData()
    {
        String url = "http://geoweb06.cti.ac.at/lbs/2016/dariia/SilverStripe_Dariia/index.php/request/getLocations";
        String result;
        ArrayList<Theater> theaters = new ArrayList<>();

        try
        {
            InputStream inputStream = new URL(url).openStream();
            result = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next(); //ISO8859-1
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonobject;

            for(int i = 0; i < jsonArray.length(); i++)
            {
                jsonobject = jsonArray.getJSONObject(i);
                Theater theater = new Theater();
                theater.setTheaterName(jsonobject.getString("Title"));
                theater.setTheaterLocation(jsonobject.getString("ShortDescription"));
                theater.setTheaterDescription(jsonobject.getString("LongDescription"));
                theater.setTheaterLatitude(Double.parseDouble(jsonobject.getString("Latitude")));
                theater.setTheaterLongitude(Double.parseDouble(jsonobject.getString("Longitude")));
                theater.setThumbImage(jsonobject.getString("ThumbImageLink"));
                theater.setDetailImage(jsonobject.getString("DetailImageLink"));

                theaters.add(theater);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return theaters;
    }
}
