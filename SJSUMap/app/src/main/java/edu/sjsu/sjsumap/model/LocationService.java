package edu.sjsu.sjsumap.model;

/**
 * Created by jagruti on 10/26/16.
 */
public class LocationService {
    private static LocationService INSTANCE = null;

    private AddressInfo addressInfo = null;

    public static LocationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocationService();
        }
        return INSTANCE;
    }

    private LocationService() {
    }


    public void setBuildingDetails(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public AddressInfo getBuildingDetails() {
        return addressInfo;
    }

    public void reset() {
        addressInfo = null;
    }
}
