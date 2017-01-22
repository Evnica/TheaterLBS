package com.evnica.theaterlbs.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.connect.GetImageTask;
import com.evnica.theaterlbs.connect.LocalDBHandler;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class DBDetailActivity extends FragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_detail);

        String[] theaterAsArray = getIntent().getStringArrayExtra("theaterAsArray");
        final String name = theaterAsArray[0];

        String coordinates = theaterAsArray[3] + ", " + theaterAsArray[4];
        ((TextView)findViewById(R.id.name)).setText(name);
        ((TextView)findViewById(R.id.description)).setText(theaterAsArray[2]);
        ((TextView)findViewById(R.id.coords)).setText(coordinates);

        ImageView image = (ImageView)findViewById(R.id.imageView);
        new GetImageTask(image).execute(theaterAsArray[6]);

        Button back = (Button)findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Button delete = (Button)findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(name);
            }
        });

    }

    private void delete(String name)
    {
        LocalDBHandler dbHandler = new LocalDBHandler(this);
        if (dbHandler.deleteTheater(name))
        {
            Toast.makeText(this,
                    "Successfully deleted", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,
                    "Can't delete from DB", Toast.LENGTH_LONG).show();
        }

    }

}
