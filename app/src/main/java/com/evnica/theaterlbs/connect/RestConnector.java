package com.evnica.theaterlbs.connect;

import com.evnica.theaterlbs.model.Theater;

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
                theater.setName(jsonobject.getString("Title"));
                theater.setAddress(jsonobject.getString("ShortDescription"));
                theater.setDescription(jsonobject.getString("LongDescription"));
                theater.setLatitude(Double.parseDouble(jsonobject.getString("Latitude")));
                theater.setLongitude(Double.parseDouble(jsonobject.getString("Longitude")));
                theater.setThumbImageLink(jsonobject.getString("ThumbImageLink"));
                theater.setDetailImageLink(jsonobject.getString("DetailImageLink"));

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
