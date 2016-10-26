package edu.sjsu.sjsumap.model;

import android.widget.ImageView;

/**
 * Created by jagruti on 10/20/16.
 */
public class BuildingInfo {
    float x1 = 0;
    float x2 = 0;
    float x3 = 0;
    float x4 = 0;

    float y1 = 0;
    float y2 = 0;

    LatLong latLong;

    String name;
    String address;

    int imageId;

    String distance;
    String time;

    public BuildingInfo(float x1, float x2, float y1, float y2) {
        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isTheBuilding(float x, float y) {
        if (x1 <= x && x <= x2 && y1 <= y && y <= y2) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public LatLong getLatLong() {
        return latLong;
    }

    public void setLatLong(LatLong latLong) {
        this.latLong = latLong;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
