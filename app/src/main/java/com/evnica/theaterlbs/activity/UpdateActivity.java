package com.evnica.theaterlbs.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.connect.LocalDBHandler;
import com.evnica.theaterlbs.model.Theater;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class UpdateActivity extends FragmentActivity
{

    String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_theater);

        mName = getIntent().getStringExtra("name");

        final EditText name = (EditText)findViewById(R.id.name);
        final EditText address = (EditText)findViewById(R.id.address);
        final EditText description = (EditText)findViewById(R.id.description);
        final EditText latitude = (EditText)findViewById(R.id.latitude);
        final EditText longitude = (EditText)findViewById(R.id.longitude);

        LocalDBHandler dbHandler = new LocalDBHandler(this);
        final Theater current = dbHandler.getTheater(mName);

        name.setText(current.getName());
        name.setEnabled(false);
        address.setText(current.getAddress());
        description.setText(current.getDescription());
        String lat = current.getLatitude() + "";
        latitude.setText(lat);
        String lon = current.getLongitude() + "";
        longitude.setText(lon);

        Button cancel = (Button)findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button submit = (Button)findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double x, y;
                try
                {
                    x = Double.parseDouble(longitude.getText().toString());
                    y = Double.parseDouble(latitude.getText().toString());

                Theater t = new Theater(name.getText().toString(),
                        address.getText().toString(), description.getText().toString(), x, y);
                update(current.getName(), t);
                }
                catch (Exception e)
                {
                    notifyEvent("Lat or Lon not numerical");
                }
            }
        });


    }

    private void update(String currentName, Theater updated)
    {
        LocalDBHandler dbHandler = new LocalDBHandler(this);
        if (dbHandler.updateTheater(currentName, updated))
        {
            Toast.makeText(this,
                    "Successfully updated", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,
                    "Can't update", Toast.LENGTH_LONG).show();
        }

    }

    private void notifyEvent(String message)
    {
        Toast.makeText(this,
                message, Toast.LENGTH_LONG).show();
    }




}
