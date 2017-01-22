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
            overlay.setContext(getActivity());
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
                double detlaLon = lonMax - lonMin;
                GeoPoint center = new GeoPoint((latMax - latMin) / 2 + latMin,
                        (detlaLon) / 2 + lonMin);
                controller.setCenter(center);
                controller.setZoom(calcZoomLevel(detlaLon));
            }

            mMap.getOverlays().add(overlay);

        }
    }

    private int calcZoomLevel(double deltaLon)
    {
        if (deltaLon > 180) return 1;
        if (deltaLon > 90) return 2;
        if (deltaLon > 45) return 3;
        if (deltaLon > 22.5) return 4;
        if (deltaLon > 11.25) return 5;
        if (deltaLon > 5.625) return 6;
        if (deltaLon > 2.813) return 7;
        if (deltaLon > 1.406) return 8;
        if (deltaLon > 0.703) return 9;
        if (deltaLon > 0.352) return 10;
        if (deltaLon > 0.176) return 11;
        if (deltaLon > 0.088) return 12;
        if (deltaLon > 0.044) return 13;
        if (deltaLon > 0.022) return 14;
        if (deltaLon > 0.011) return 15;
        if (deltaLon > 0.005) return 16;
        if (deltaLon > 0.003) return 17;
        if (deltaLon > 0.001) return 18;
        else return 19;
    }

}
