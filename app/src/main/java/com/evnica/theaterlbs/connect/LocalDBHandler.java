package com.evnica.theaterlbs.connect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.evnica.theaterlbs.model.Theater;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by: Evnica
 * Date: 20.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class LocalDBHandler extends SQLiteOpenHelper {

    //Database
    private static final String DATABASE_NAME = "Theaters";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_THEATERS = "theaters";

    //Database SCHEMA
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LONGITUDE = "lon";
    private static final String KEY_LATITUDE = "lat";
    private static final String KEY_THUMB_IMAGE = "thumb_image";
    private static final String KEY_DETAIL_IMAGE = "detail_image";

    public LocalDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_THEATERS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_ADDRESS + " TEXT, "
                + KEY_DESCRIPTION + " TEXT, "
                + KEY_LONGITUDE + " REAL, "
                + KEY_LATITUDE + " REAL, "
                + KEY_THUMB_IMAGE + " TEXT, "
                + KEY_DETAIL_IMAGE + " TEXT )";

        db.execSQL(CREATE_LOCATION_TABLE);
    }

    public boolean addTheater(Theater theater)
    {
        boolean result;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_THEATERS +
                      " WHERE name = \"" + theater.getName() + "\"";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            result = false;
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, theater.getName());
            values.put(KEY_ADDRESS, theater.getAddress());
            values.put(KEY_DESCRIPTION, theater.getDescription());
            values.put(KEY_LONGITUDE, theater.getLongitude());
            values.put(KEY_LATITUDE, theater.getLatitude());
            values.put(KEY_THUMB_IMAGE, theater.getThumbImageLink());
            values.put(KEY_DETAIL_IMAGE, theater.getDetailImageLink());

            db.insert(TABLE_THEATERS, null, values);
            cursor.close();
            db.close();
            result = true;
        }

        return result;
    }

    public List<Theater> getTheaters(){

        List<Theater> locations = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_THEATERS;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){

            Theater theater = new Theater();
            theater.setName(cursor.getString(1));
            theater.setAddress(cursor.getString(2));
            theater.setDescription(cursor.getString(3));
            theater.setLongitude(cursor.getFloat(4));
            theater.setLatitude(cursor.getFloat(5));
            theater.setThumbImageLink(cursor.getString(6));
            theater.setDetailImageLink(cursor.getString(7));

            locations.add(theater);
        }

        cursor.close();
        db.close();
        return  locations;
    }

    public boolean deleteTheater(String name)
    {
        boolean result;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_THEATERS, KEY_NAME + " = ? ", new String[]{name});
            db.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
