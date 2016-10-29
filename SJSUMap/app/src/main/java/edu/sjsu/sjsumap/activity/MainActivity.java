package edu.sjsu.sjsumap.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.maps.model.LatLng;

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
import edu.sjsu.sjsumap.model.CampusInfo;
import edu.sjsu.sjsumap.model.LatLong;
import edu.sjsu.sjsumap.model.LocationService;
import edu.sjsu.sjsumap.tasks.GoogleAPITask;

public class MainActivity extends AppCompatActivity {

    private ImageView map = null;
    private EditText searchBar = null;
    private Button search = null;
    private Button clear = null;
    final private int ACCESS_COARSE_LOCATION = 1;
    List<BuildingInfo> buildingInfoList = CampusInfo.getInstance().getBuildingInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = (ImageView) findViewById(R.id.map);

        searchBar = (EditText) findViewById(R.id.searchbar);
        search = (Button) findViewById(R.id.searchbutton);
        clear = (Button) findViewById(R.id.clear);

        requestPermissions();
        getCurrentLocation();

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float[] coordinates = new float[2];
                    coordinates[0] = event.getX();
                    coordinates[1] = event.getY();

                    int[] imageLocation = new int[2];
                    //get image coordinates
                    map.getLocationOnScreen(imageLocation);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    ImageView imageView = new ImageView(getApplicationContext());
                    layoutParams.setMargins((int)coordinates[0], (int)coordinates[1], 0, 0);
                    imageView.setLayoutParams(layoutParams);

                    //TODO find out valid building click
                    for (BuildingInfo buildingInfo : buildingInfoList) {
                        if (buildingInfo.isTheBuilding(coordinates[0], coordinates[1])) {
                            try {
                                if (LocationService.getInstance().getCurrentLocation() != null) {
                                    LocationService.getInstance().setBuildingDetails(buildingInfo);
                                    new GoogleAPITask(getApplicationContext()).execute(getGoogleAPIURL(LocationService.getInstance().getCurrentLocation(), buildingInfo.getLatLong()));
                                } else {
                                    requestPermissions();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return true;
            }
        });

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, 0);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }

                String search = searchBar.getText().toString();
                highlightSelectedBuilding(search);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.getText().clear();
                BuildingInfo buildingInfo= LocationService.getInstance().getBuildingDetails();

                for (BuildingInfo buildingInfo1 : buildingInfoList) {
                    ImageView pin = (ImageView) findViewById(buildingInfo1.getPinId());
                    pin.setVisibility(View.INVISIBLE);
                }
            }
        });
}

    private void highlightSelectedBuilding(String search) {
        //make the selected visible
        search = search.toLowerCase();
        for (BuildingInfo buildingInfo : buildingInfoList) {
            String buildingLowerCase = buildingInfo.getName().toLowerCase();
            if (buildingLowerCase.contains(search)) {
                showTapInfo("You Selected: " + buildingInfo.getName());
                ImageView pin = (ImageView) findViewById(buildingInfo.getPinId());
                pin.setVisibility(View.VISIBLE);
                LocationService.getInstance().setBuildingDetails(buildingInfo);
            }
        }
    }

    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(LOCATION_SERVICE);
        boolean isGPSOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        try {
            LocationListener locationListener =  new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LocationService.getInstance().setCurrentLocation(new LatLong(location.getLatitude(), location.getLongitude()));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

            if (!isGPSOn && !isNetworkOn) {
                showTapInfo("Please enable GPS/network");
                requestPermissions();
            }

            if (isGPSOn) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

            if (isNetworkOn) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }

            if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                LocationService.getInstance().setCurrentLocation(new LatLong(
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(),
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude()
                ));
            }

        } catch (SecurityException e) {
            e.printStackTrace();
            showTapInfo("Exception while fetching location");
            requestPermissions();
        }
    }

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LocationManager gpsStatus = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        switch (requestCode) {
            case ACCESS_COARSE_LOCATION:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!gpsStatus.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                        builder.setMessage("GPS is disabled. Enable for GPS? ")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(@SuppressWarnings("unused") DialogInterface dialog, @SuppressWarnings("unused") int which) {
                                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        android.app.AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "App requries Location to perform all Features", Toast.LENGTH_SHORT).show();
                    try {
                        LocationService.getInstance().setCurrentLocation(new LatLong(gpsStatus.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), gpsStatus.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude()));
                    } catch (SecurityException permissionException) {
                        Toast.makeText(getBaseContext(), "Exception in Fetching last known location", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
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
                "&mode=walking&key=AIzaSyASyxw9ZWTltQldm5PTGlOoOlxF9PbK_Tw";
        System.out.println("URL: " + stringURL);
        return stringURL;
    }

    private void showTapInfo(String message) {
        final AlertDialog.Builder addressBuilder = new AlertDialog.Builder(MainActivity.this);
        addressBuilder.setMessage(message);
        final AlertDialog mapAddress = addressBuilder.create();
        mapAddress.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mapAddress.dismiss();
            }
        }, 2000);
    }
}
