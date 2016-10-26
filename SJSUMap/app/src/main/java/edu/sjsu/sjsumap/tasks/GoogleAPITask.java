package edu.sjsu.sjsumap.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.sjsu.sjsumap.activity.DetailInfoActivity;
import edu.sjsu.sjsumap.model.LatLong;
import edu.sjsu.sjsumap.model.LocationService;

/**
 * Created by jagruti on 10/26/16.
 */
public class GoogleAPITask extends AsyncTask<String, Void, String> {
    String result = null;
    Context context;

    public GoogleAPITask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... stringURL) {
        URL url = null;
        StringBuffer response = null;
        try {
            url = new URL(stringURL[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();

            response = new StringBuffer();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result =  response.toString();
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            try {
            LocationService.getInstance().getBuildingDetails().setDistance(parseDistance(result));
            LocationService.getInstance().getBuildingDetails().setTime(parseTime(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent in = new Intent(context, DetailInfoActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);
    }

    private String parseDistance(String response) throws JSONException {
        JSONObject jsonRespRouteDistance = new JSONObject(response)
                .getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0)
                .getJSONObject("distance");
        return jsonRespRouteDistance.get("text").toString();
    }

    private String parseTime(String response) throws JSONException {
        JSONObject jsonRespRouteTime = new JSONObject(response)
                .getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0)
                .getJSONObject("duration");
        return jsonRespRouteTime.get("text").toString();
    }


}
