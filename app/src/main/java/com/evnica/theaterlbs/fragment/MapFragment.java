package com.evnica.theaterlbs.fragment;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evnica.theaterlbs.CustomOverlay;
import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.connect.RestConnector;
import com.evnica.theaterlbs.model.Theater;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class MapFragment extends Fragment
{
    public MapView mMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View mapView = inflater.inflate(R.layout.fragment_osm, container, false);
        mMap = (MapView) mapView.findViewById(R.id.osm);
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setBuiltInZoomControls(true);
        mMap.setMultiTouchControls(true);

        new TheaterLoader().execute();

        return mapView;
    }

    class TheaterLoader extends AsyncTask<String, Void, ArrayList<Theater>>
    {

        @Override
        protected ArrayList<Theater> doInBackground(String... params) {

            return RestConnector.getsInstance().getData();
        }

        @Override
        protected void onPostExecute(ArrayList<Theater> theaters) {
            Drawable marker = ContextCompat.getDrawable(getActivity(), R.mipmap.drama_icon);
            CustomOverlay overlay = new CustomOverlay(marker, mMap);
            IMapController controller = mMap.getController();
            double latMin, latMax;
            latMin = latMax = theaters.get(0).getLatitude();
            double lonMin, lonMax;
            lonMin = lonMax = theaters.get(0).getLongitude();

            for (Theater t: theaters)
            {
                double lat = t.getLatitude();
                double lon = t.getLongitude();
                if (latMax < lat)
                {
                    latMax = lat;
                }
                if (latMin > lat)
                {
                    latMin = lat;
                }
                if (lonMax < lon)
                {
                    lonMax = lon;
                }
                if (lonMin > lon)
                {
                    lonMin = lon;
                }
                GeoPoint point = new GeoPoint(lat, lon);
                overlay.addOverlayItem(new OverlayItem(t.getName(), t.getAddress(), point));

                GeoPoint center = new GeoPoint((latMax - latMin) / 2 + latMin,
                        (lonMax - lonMin) / 2 + lonMin);
                controller.setCenter(center);
                controller.setZoom(14);
            }

            mMap.getOverlays().add(overlay);

        }
    }

}
