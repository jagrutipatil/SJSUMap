package edu.sjsu.sjsumap.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sjsumap.R;
import edu.sjsu.sjsumap.model.AddressInfo;
import edu.sjsu.sjsumap.model.LocationService;

public class MainActivity extends AppCompatActivity {

    private ImageView map = null;
    List<AddressInfo> addressInfoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = (ImageView) findViewById(R.id.map);

        //TODO create the Address info objects with building
        //131- 257 x wide,668 - 862 long
        AddressInfo kingsLibrary = new AddressInfo(131, 257, 668, 862);
        kingsLibrary.setName("King Library");
        kingsLibrary.setAddress("Dr. Martin Luther King, Jr. Library,\n150 East San Fernando Street,\nSan Jose, CA 95112");
        kingsLibrary.setImageId(R.drawable.kingsLibrary);
        addressInfoList.add(kingsLibrary);

        //engg building

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float[] coordinates = new float[2];
                coordinates[0] = event.getX();
                coordinates[1] = event.getY();

                for (AddressInfo addressInfo : addressInfoList) {
                    if (addressInfo.isTheBuilding(coordinates[0], coordinates[1])) {
                        showTapInfo(addressInfo);
                    }
                }
                //TODO show image and calculate distance using google api
                // what to use as a current location?
                return false;
            }
        });
    }

    private void showTapInfo(AddressInfo addressInfo) {
        final AlertDialog.Builder addressBuilder = new AlertDialog.Builder(MainActivity.this);
        addressBuilder.setMessage(addressInfo.getAddress());
        final AlertDialog mapAddress = addressBuilder.create();
        mapAddress.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mapAddress.dismiss();
            }
        }, 2000);

        LocationService.getInstance().setBuildingDetails(addressInfo);
        Intent intent = new Intent(this, DetailInfoActivity.class);
        startActivity(intent);
    }
}
