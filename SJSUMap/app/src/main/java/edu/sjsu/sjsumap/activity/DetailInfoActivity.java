package edu.sjsu.sjsumap.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
        final double latitude = buildingInfo.getStreetViewLatLng().getLatitude();
        final double longitude = buildingInfo.getStreetViewLatLng().getLongitude();
        locImage.setImageResource(buildingInfo.getImageId());

        //TODO harcoded lat, long
        streetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent streetView = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.streetview:cbll=" + latitude +","+ longitude +"&cbp=1,99.56,,1,-5.27&mz=21"));
                streetView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(streetView);
            }
        });
    }
}
