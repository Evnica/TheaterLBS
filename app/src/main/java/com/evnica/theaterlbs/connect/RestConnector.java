package com.evnica.theaterlbs.connect;

import android.util.Log;

import com.evnica.theaterlbs.model.Theater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

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

    public boolean addTheaterToBackend(String name,
                                       String longitude ,
                                       String latitude,
                                       String address,
                                       String description,
                                       String detailImageString,
                                       String thumbImageString) {

        String backend =
        "http://geoweb06.cti.ac.at/lbs/2016/dariia/SilverStripe_Dariia/index.php/request/addLocation";

        URL url;
        String response = "Failed";
        try {
            url = new URL(backend);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Request Body
            HashMap<String, String> params = new HashMap<>();
            params.put("Name", name);
            params.put("ShortDescription", address);
            params.put("LongDescription", description);
            params.put("Longitude", longitude);
            params.put("Latitude", latitude);
            params.put("thumb_image_base64", thumbImageString);
            params.put("detail_image_base64", detailImageString);

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            // getPostDataString makes a string from parameters according to HTTP standards
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            outputStream.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                response = new Scanner(conn.getInputStream(), "UTF-8").useDelimiter("\\A").next();
                Log.d("NETWORK", response);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return !response.equals("Failed");
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
