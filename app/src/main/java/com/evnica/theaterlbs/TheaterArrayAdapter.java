package com.evnica.theaterlbs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evnica.theaterlbs.entity.Theater;

import java.util.ArrayList;

/**
 * Created by: Evnica
 * Date: 20.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class TheaterArrayAdapter extends ArrayAdapter<Theater> {

    private Context mContext;
    private ArrayList<Theater> mTheaters;

    public TheaterArrayAdapter(Context context, ArrayList<Theater> theaters){
        super(context, R.layout.theater_row_layout, theaters);
        mContext = context;
        mTheaters = theaters;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.theater_row_layout, parent, false);
        TextView theaterName = (TextView)rowView.findViewById(R.id.theaterName);
        TextView theaterAddress = (TextView)rowView.findViewById(R.id.theaterAddress);
        ImageView image = (ImageView)rowView.findViewById(R.id.imageView);

        Theater currentTheater = mTheaters.get(position);
        theaterName.setText(currentTheater.getTheaterName());
        theaterAddress.setText(currentTheater.getTheaterLocation());

        return rowView;
    }

}
