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


        BuildingInfo engg = new BuildingInfo(673, 860, 614, 795);
        engg.setName("Engineering Building");
        engg.setAddress("San JosÃ© State University Charles W. Davidson College of Engineering, 1 Washington Square,\n San Jose, CA 95112");
        engg.setLatLong(new LatLong(37.337007, -121.881600));
        engg.setImageId(R.drawable.eng_building);
        engg.setPinId(R.id.pin_engg);
        buildingInfoList.add(engg);


        BuildingInfo yosh_hall = new BuildingInfo(135, 228, 1042, 1159);
        yosh_hall.setName("Yoshihiro Uchida Hall");
        yosh_hall.setAddress("Yoshihiro Uchida Hall,\n San Jose, CA 95112");
        yosh_hall.setLatLong(new LatLong(37.333666, -121.883768));
        //yosh_hall.setImageId(R.drawable.yosh_hall);
        yosh_hall.setPinId(R.id.pin_yuh);
        buildingInfoList.add(yosh_hall);

        BuildingInfo student_union = new BuildingInfo(680, 834, 845, 905);
        student_union.setName("Student Union");
        student_union.setAddress("Student Union Building,\n San Jose, CA 95112");
        student_union.setLatLong(new LatLong(37.336342, -121.881289));
        //student_union.setImageId(R.drawable.student_union);
        student_union.setPinId(R.id.pin_su);
        buildingInfoList.add(student_union);


        BuildingInfo bbc = new BuildingInfo(1064, 1183, 965, 1032);
        bbc.setName("BBC");
        bbc.setAddress("Boccardo Business Complex,\n San Jose, CA 95112");
        bbc.setLatLong(new LatLong(37.336559, -121.878669));
        //bbc.setImageId(R.drawable.bbc);
        bbc.setPinId(R.id.pin_bbc);
        buildingInfoList.add(bbc);

        BuildingInfo spg = new BuildingInfo(399, 622, 1363, 1501);
        spg.setName("South Parking Garage");
        spg.setAddress("San Jose State University South Garage,\n 330 South 7th Street,\n San Jose, CA 95112");
        spg.setLatLong(new LatLong(37.333065, -121.880517));
        //spg.setImageId(R.drawable.spg);
        spg.setPinId(R.id.pin_spg);
        buildingInfoList.add(spg);
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
