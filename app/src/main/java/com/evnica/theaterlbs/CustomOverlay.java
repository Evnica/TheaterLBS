package com.evnica.theaterlbs;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class CustomOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> mOverlayItems = new ArrayList<OverlayItem>();
    private MapView mMapView;

    public CustomOverlay(Drawable drawable, MapView mapView) {
        super(drawable);
        this.mMapView = mapView;
    }

    public void addOverlayItem(OverlayItem item) {
        mOverlayItems.add(item);
        populate();
    }

    @Override
    protected OverlayItem createItem(int index) {
        return mOverlayItems.get(index);
    }

    @Override
    public int size() {
        return mOverlayItems.size();
    }

    @Override
    protected boolean onTap(int index) {
        return true;
    }

    @Override
    public boolean onSnapToItem(int i, int i1, Point point, IMapView iMapView) {
        return false;
    }
}
