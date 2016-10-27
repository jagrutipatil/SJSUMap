package edu.sjsu.sjsumap.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sjsumap.R;
import edu.sjsu.sjsumap.model.BuildingInfo;
import edu.sjsu.sjsumap.model.LatLong;
import edu.sjsu.sjsumap.model.LocationService;
import edu.sjsu.sjsumap.tasks.GoogleAPITask;

public class MainActivity extends AppCompatActivity {

    private ImageView map = null;
    List<BuildingInfo> buildingInfoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = (ImageView) findViewById(R.id.map);

        //TODO add this locations to campus info and access from there
        //TODO create the Address info objects with building
        //131- 257 x wide,668 - 862 long
        BuildingInfo kingsLibrary = new BuildingInfo(131, 257, 668, 862);

        kingsLibrary.setName("King Library");
        kingsLibrary.setAddress("Dr. Martin Luther King, Jr. Library,\n150 East San Fernando Street,\nSan Jose, CA 95112");
        kingsLibrary.setLatLong(new LatLong(37.335304, -121.885063));
        kingsLibrary.setImageId(R.drawable.kingslibrary);
        buildingInfoList.add(kingsLibrary);

        final LatLong userLocation = new LatLong(37.337476, -121.881539);
        //engg building
        //lati: 37.337476, long:

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float[] coordinates = new float[2];
                coordinates[0] = event.getX();
                coordinates[1] = event.getY();

                for (BuildingInfo buildingInfo : buildingInfoList) {
                    if (buildingInfo.isTheBuilding(coordinates[0], coordinates[1])) {
                        try {
                            LocationService.getInstance().setBuildingDetails(buildingInfo);
                            new GoogleAPITask(getApplicationContext()).execute(getGoogleAPIURL(userLocation, buildingInfo.getLatLong()));
                            showTapInfo(buildingInfo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //TODO calculate distance using google api
                return false;
            }
        });
    }

    private String getGoogleAPIURL(LatLong currentLocation, LatLong destination) throws IOException {
        //Google matrix API url
        String stringURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" +
                Double.toString(currentLocation.getLatitude()) +
                "," +
                Double.toString(currentLocation.getLongitude()) +
                "&destinations=" +
                Double.toString(destination.getLatitude()) +
                "," +
                Double.toString(destination.getLongitude()) +
                "&mode=" + "bicycling";
        System.out.println("URL: " + stringURL);
        return stringURL;
    }

    private void showTapInfo(BuildingInfo buildingInfo) {
        final AlertDialog.Builder addressBuilder = new AlertDialog.Builder(MainActivity.this);
        addressBuilder.setMessage(buildingInfo.getAddress());
        final AlertDialog mapAddress = addressBuilder.create();
        mapAddress.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mapAddress.dismiss();
            }
        }, 2000);

//        LocationService.getInstance().setBuildingDetails(buildingInfo);
//        Intent intent = new Intent(this, DetailInfoActivity.class);
//        startActivity(intent);
    }
}
