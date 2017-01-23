package com.evnica.theaterlbs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evnica.theaterlbs.GlobalVars;
import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.connect.GetImageTask;
import com.evnica.theaterlbs.connect.LocalDBHandler;

import java.text.DecimalFormat;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class DBDetailActivity extends FragmentActivity
{
    String imageLink;
    DecimalFormat decimalFormat = new DecimalFormat("###.0#######");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_detail);

        String[] theaterAsArray = getIntent().getStringArrayExtra("theaterAsArray");
        String name = theaterAsArray[0];
        String coordinates = decimalFormat.format(Double.parseDouble(theaterAsArray[3])) + ", "
                           + decimalFormat.format(Double.parseDouble(theaterAsArray[4]));
        ((TextView)findViewById(R.id.name)).setText(name);
        ((TextView)findViewById(R.id.description)).setText(theaterAsArray[2]);
        ((TextView)findViewById(R.id.coords)).setText(coordinates);
        ImageView image = (ImageView)findViewById(R.id.imageView);
        imageLink = theaterAsArray[6];
        new GetImageTask(image).execute(imageLink);

        addButtons(name);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalVars.justUpdated)
        {
            GlobalVars.justUpdated = false;
            setContentView(R.layout.activity_db_detail);
            String coords = decimalFormat.format(GlobalVars.updatedTheater.getLatitude()) + ", "
                          + decimalFormat.format(GlobalVars.updatedTheater.getLongitude());
            ((TextView)findViewById(R.id.name)).setText(GlobalVars.updatedTheater.getName());
            ((TextView)findViewById(R.id.description)).setText(GlobalVars.updatedTheater.getDescription());
            ((TextView)findViewById(R.id.coords)).setText(coords);
            ImageView image = (ImageView)findViewById(R.id.imageView);
            new GetImageTask(image).execute(imageLink);

            addButtons(GlobalVars.updatedTheater.getName());
        }

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

    private void addButtons(final String name)
    {
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
                finish();
            }
        });

        Button update = (Button)findViewById(R.id.update);
        final Intent intent = new Intent(this, UpdateActivity.class);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

}
