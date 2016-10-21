package edu.sjsu.sjsumap;

import android.widget.ImageView;

/**
 * Created by jagruti on 10/20/16.
 */
public class AddressInfo {
    float x1 = 0;
    float x2 = 0;
    float x3 = 0;
    float x4 = 0;

    float y1 = 0;
    float y2 = 0;

    int latitude;
    int longitude;

    String name;
    String address;

    ImageView imageView = null;
    //link for street view

    public AddressInfo(float x1, float x2, float y1, float y2) {
        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;
    }

    boolean isTheBuilding(float x, float y) {
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
}
