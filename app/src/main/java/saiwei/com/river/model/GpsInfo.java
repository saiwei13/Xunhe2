package saiwei.com.river.model;

import com.amap.api.maps2d.model.LatLng;

/**
 * Created by saiwei on 9/24/17.
 */

public class GpsInfo {

    LatLng latLng;

    String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
