package com.example.eve.demobbva;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String s;
    public LocationManager locationManager;
    public LocationListener locationListener;
    public ArrayList<BankInfo> bankInfos;

    @TargetApi(Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        s = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=41.9172239,-88.2676646&radius=10000&key=AIzaSyAcT4KuO69Zrf2son67pNZbt96RkO9IokQ";
        bankInfos = Presenter.getInstance().getArray();
        searchBBVA();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
        } else {
           // locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Toast.makeText(MapsActivity.this, "Showing Map",Toast.LENGTH_LONG).show();
        }
        if (id == R.id.item2) {
            Toast.makeText(MapsActivity.this, "Show List",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MapsActivity.this, ListActivity.class);
            startActivity(intent);

        }

        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        };

        timerThread.start();

        LatLng chicago = new LatLng(41.9172239, -88.2676646);
        mMap.addMarker(new MarkerOptions().
                position(chicago).
                title("Your location at Chicago"));
/*

        for (int i = 0; i < bankInfos.size(); i++) {
            mMap.addMarker(new MarkerOptions().
                    position(new LatLng(Double.parseDouble(bankInfos.get(i).getLat()),
                            Double.parseDouble(bankInfos.get(i).getLng()))).
                    title(bankInfos.get(i).getName()));

        }
*/

        mMap.moveCamera(CameraUpdateFactory.newLatLng(chicago));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(8));


    }

    public void searchBBVA() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, s, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    if (response.contains("OK")) {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject infoObj = jsonArray.getJSONObject(i);
                            String lat = infoObj.getJSONObject("geometry").
                                    getJSONObject("location")
                                    .getString("lat");

                            String lng = infoObj.getJSONObject("geometry").
                                    getJSONObject("location")
                                    .getString("lng");

                            BankInfo info = new BankInfo(infoObj.getString("formatted_address"),
                                    lat, lng, infoObj.getString("icon"),
                                    infoObj.getString("id"),infoObj.getString("name"),
                                    infoObj.getString("place_id"),infoObj.getString("types"));

                            bankInfos.add(info);

                        }
                        for (int i = 0; i < bankInfos.size(); i++) {
                            mMap.addMarker(new MarkerOptions().
                                    position(new LatLng(Double.parseDouble(bankInfos.get(i).getLat()),
                                            Double.parseDouble(bankInfos.get(i).getLng()))).
                                    title(bankInfos.get(i).getName()));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



}
