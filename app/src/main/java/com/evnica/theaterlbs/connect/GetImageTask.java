package com.evnica.theaterlbs.connect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */
public class GetImageTask extends AsyncTask<String, Void, Bitmap>
{
    private ImageView image;

    public GetImageTask(ImageView image)
    {
        this.image = image;
    }

    // get an image
    @Override
    protected Bitmap doInBackground(String... params)
    {

        Bitmap thumbImage = null;

        try{
            InputStream in = new URL(params[0]).openStream();  // params[0] is image path link
            thumbImage = BitmapFactory.decodeStream(in);

        } catch (Exception e){
            e.printStackTrace();
        }
        return thumbImage;
    }

    // display the image in the row
    protected void onPostExecute(Bitmap bitmap)
    {
        image.setImageBitmap(bitmap);
    }

}