package com.evnica.theaterlbs.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.connect.RestConnector;

import java.io.ByteArrayOutputStream;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class AddTheaterFragment extends Fragment
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_add_theater, container, false);

        final EditText name = (EditText)fragmentView.findViewById(R.id.name);
        final EditText address = (EditText)fragmentView.findViewById(R.id.address);
        final EditText description = (EditText)fragmentView.findViewById(R.id.description);
        final EditText latitude = (EditText)fragmentView.findViewById(R.id.latitude);
        final EditText longitude = (EditText)fragmentView.findViewById(R.id.longitude);
        Button submit = (Button)fragmentView.findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (name.getText() != null && name.getText().toString().length() > 0 &&
                        address.getText() != null && address.getText().toString().length() > 0 &&
                        description.getText() != null && description.getText().toString().length() > 0 &&
                        longitude.getText() != null && longitude.getText().toString().length() > 0 &&
                        latitude.getText() != null && latitude.getText().toString().length() > 0
                        )
                {
                    try{
                        Double.parseDouble(longitude.getText().toString());
                        Double.parseDouble(latitude.getText().toString());

                        // sent a drawable
                        Bitmap icon = BitmapFactory
                                .decodeResource(getResources(), R.mipmap.drama_icon);
                        // create a stream to save a picture
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        icon.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        String base64thumbImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                        Bitmap icon2 = BitmapFactory
                                .decodeResource(getResources(), R.mipmap.drama_icon);
                        // create a stream to save a picture
                        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                        icon2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                        byte[] bytes2 = byteArrayOutputStream2.toByteArray();
                        String base64detailImage = Base64.encodeToString(bytes2, Base64.DEFAULT);


                        new PostTask(name.getText().toString(),
                                address.getText().toString(),
                                description.getText().toString(),
                                latitude.getText().toString(),
                                longitude.getText().toString(),
                                base64thumbImage, base64detailImage).execute();

                        Toast.makeText(getActivity(),
                                "Done", Toast.LENGTH_LONG).show();

                        name.setText("");
                        address.setText("");
                        description.setText("");
                        latitude.setText("");
                        longitude.setText("");
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getActivity(),
                                "Latitude or longitude not numeric", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),
                            "One of the fields is empty", Toast.LENGTH_LONG).show();
                }

            }
        });


        return fragmentView;
    }

    private class PostTask extends AsyncTask<String, Void, Boolean> {

        String name, address, description, latitude, longitude,
                thumbImage, detailImage;

        PostTask(String name, String address, String description,
                 String latitude, String longitude, String thumbImage, String detailImage) {
            this.name = name;
            this.address = address;
            this.description = description;
            this.latitude = latitude;
            this.longitude = longitude;
            this.thumbImage = thumbImage;
            this.detailImage = detailImage;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return RestConnector.getsInstance().addTheaterToBackend(name, longitude,
                    latitude, address, description, detailImage, thumbImage);

        }
    }
}
