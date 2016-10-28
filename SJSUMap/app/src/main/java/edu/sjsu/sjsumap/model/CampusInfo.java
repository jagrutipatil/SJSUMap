package edu.sjsu.sjsumap.model;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sjsumap.R;

/**
 * Created by jagruti on 10/26/16.
 */
public class CampusInfo {
    List<BuildingInfo> buildingInfoList = new ArrayList<>();

    private static CampusInfo instance = null;

    private CampusInfo () {
        BuildingInfo kingsLibrary = new BuildingInfo(131, 257, 668, 862);
        kingsLibrary.setName("King Library");
        kingsLibrary.setAddress("Dr. Martin Luther King, Jr. Library,\n150 East San Fernando Street,\nSan Jose, CA 95112");
        kingsLibrary.setLatLong(new LatLong(37.335304, -121.885063));
        kingsLibrary.setImageId(R.drawable.kingslibrary);
        kingsLibrary.setPinId(R.id.pin_library);
        buildingInfoList.add(kingsLibrary);


        BuildingInfo engg = new BuildingInfo(131, 257, 668, 862);
        engg.setName("Engineering Building");
        engg.setAddress("San José State University Charles W. Davidson College of Engineering, 1 Washington Square,\n San Jose, CA 95112");
        engg.setLatLong(new LatLong(37.335142, -121.881276));
        engg.setImageId(R.drawable.eng_building);
        buildingInfoList.add(engg);

    }

    public static CampusInfo getInstance() {
        if (instance == null) {
            instance = new CampusInfo();
        }
        return instance;
    }

    public List<BuildingInfo> getBuildingInfo() {
        return buildingInfoList;
    }
}
