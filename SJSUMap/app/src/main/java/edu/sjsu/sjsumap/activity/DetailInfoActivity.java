package edu.sjsu.sjsumap.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.sjsu.sjsumap.R;
import edu.sjsu.sjsumap.model.BuildingInfo;
import edu.sjsu.sjsumap.model.LocationService;

//TODO backpress

public class DetailInfoActivity extends AppCompatActivity {
    TextView name;
    TextView address;
    ImageView locImage;
    TextView time;
    TextView distance;
    Button streetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        name = (TextView) findViewById(R.id.buildingName);
        address = (TextView) findViewById(R.id.bAddress);
        time = (TextView) findViewById(R.id.time);
        distance = (TextView) findViewById(R.id.tDistance);
        streetView = (Button) findViewById(R.id.streetView);
        locImage = (ImageView)findViewById(R.id.locimage);

        BuildingInfo buildingInfo = LocationService.getInstance().getBuildingDetails();
        name.setText(buildingInfo.getName());
        address.setText(buildingInfo.getAddress());
        distance.setText(buildingInfo.getDistance());
        time.setText(buildingInfo.getTime());

        locImage.setImageResource(buildingInfo.getImageId());
    }


}
