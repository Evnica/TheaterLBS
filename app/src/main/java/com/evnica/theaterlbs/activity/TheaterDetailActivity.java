package com.evnica.theaterlbs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.connect.GetImageTask;
import com.evnica.theaterlbs.connect.LocalDBHandler;
import com.evnica.theaterlbs.model.Theater;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class TheaterDetailActivity extends Activity

{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String[] theaterAsArray = getIntent().getStringArrayExtra("theaterAsArray");

        String coordinates = theaterAsArray[3] + ", " + theaterAsArray[4];

        ((TextView)findViewById(R.id.name)).setText(theaterAsArray[0]);
        ((TextView)findViewById(R.id.address)).setText(theaterAsArray[1]);
        ((TextView)findViewById(R.id.coords)).setText(coordinates);
        ((TextView)findViewById(R.id.description)).setText(theaterAsArray[2]);

        ImageView image = (ImageView)findViewById(R.id.imageView);

        new GetImageTask(image).execute(theaterAsArray[6]);

        final Theater theater = new Theater();
        theater.setName(theaterAsArray[0]);
        theater.setAddress(theaterAsArray[1]);
        theater.setDescription(theaterAsArray[2]);
        theater.setLatitude(Double.parseDouble(theaterAsArray[3]));
        theater.setLongitude(Double.parseDouble(theaterAsArray[4]));
        theater.setThumbImageLink(theaterAsArray[5]);
        theater.setDetailImageLink(theaterAsArray[6]);

        Button back = (Button)findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               add(theater);
            }
        });
    }

    private void add(Theater theater)
    {
        LocalDBHandler dbHandler = new LocalDBHandler(this);
        if (dbHandler.addTheater(theater))
        {
            Toast.makeText(this,
                    "Theater added to DB", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,
                    "This theater is already in DB", Toast.LENGTH_LONG).show();
        }
    }
}
