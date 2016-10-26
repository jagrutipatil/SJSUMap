package edu.sjsu.sjsumap.model;

/**
 * Created by jagruti on 10/26/16.
 */
public class LocationService {
    private static LocationService INSTANCE = null;

    private BuildingInfo buildingInfo = null;

    public static LocationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocationService();
        }
        return INSTANCE;
    }

    private LocationService() {
    }


    public void setBuildingDetails(BuildingInfo buildingInfo) {
        this.buildingInfo = buildingInfo;
    }

    public BuildingInfo getBuildingDetails() {
        return buildingInfo;
    }

    public void reset() {
        buildingInfo = null;
    }
}
