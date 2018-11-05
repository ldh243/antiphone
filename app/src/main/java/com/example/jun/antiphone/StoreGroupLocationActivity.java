package com.example.jun.antiphone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jun.antiphone.singleton.RestfulAPIManager;
import com.example.jun.antiphone.singleton.VolleyCallback;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import entity.StoreDTO;

public class StoreGroupLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static long LOCATION_REFRESH_TIME = 1000 * 60 * 10;
    private final static long LOCATION_REFRESH_DISTANCE = 100000000;
    private SupportMapFragment mapFragment;
    private ProgressDialog myProgress;
    private ListView lvStores;
    private GoogleMap gmap;
    private int storeGroupId;
    private List<StoreDTO> stores;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
//            getStores(location);
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

    private void configToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarMap);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_group_location);
        configToolbar();
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Map Loading ...");
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);
        myProgress.show();

        storeGroupId = getIntent().getIntExtra("storeGroupID", -1);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);

        lvStores = findViewById(R.id.lvStores);
        lvStores.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setMap(new LatLng(
                        Double.parseDouble(stores.get(position).getStoreLatitude()),
                        Double.parseDouble(stores.get(position).getStoreLongitude())));
            }

        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        gmap = googleMap;

        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gmap.getUiSettings().setZoomControlsEnabled(true);


        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Not permission
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);

        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        setMap(new LatLng(location.getLatitude(), location.getLongitude()));
        setMarker(new LatLng(location.getLatitude(), location.getLongitude()), "My location", true);
        getStores(location);
    }

    private void setMarker(LatLng location, String title, boolean isMyLocation) {
        MarkerOptions markerOptions = new MarkerOptions().position(location)
                .title(title);
        markerOptions.icon(
                isMyLocation ? BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                : BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        gmap.addMarker(markerOptions);
    }

    private void setMap(LatLng location) {
        CameraPosition INIT = new CameraPosition.Builder().target(location).zoom(15.5F).bearing(300F)
                .tilt(50F)
                .build();
        gmap.moveCamera(CameraUpdateFactory.newCameraPosition(INIT));

    }

    private void getStores(Location location) {

        final RestfulAPIManager api = RestfulAPIManager.getInstancẹ̣̣̣();

        final String origin = location.getLatitude() + "," + location.getLongitude();


        api.getStoresInStoreGroup(this, storeGroupId, new VolleyCallback() {

            @Override
            public void onSuccess(Object response) {

                JSONArray arrays = (JSONArray) response;

                if (arrays.length() > 0) {

                    stores = new ArrayList<>();

                    ObjectMapper mapper = new ObjectMapper();
                    String destination = "";

                    for (int i = 0; i < arrays.length(); i++) {
                        try {
                            final StoreDTO store = mapper.readValue(arrays.get(i).toString(), StoreDTO.class);
                            destination += store.getStoreLatitude() + "," + store.getStoreLongitude() + "|";
                            setMarker(new LatLng(Double.parseDouble(store.getStoreLatitude()),
                                    Double.parseDouble(store.getStoreLongitude())), store.getStoreAddress(), false);
                            stores.add(store);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    destination = destination.substring(0, destination.length() - 1);

                    api.getStoreDistance(getApplicationContext(), origin, destination, new VolleyCallback() {
                        @Override
                        public void onSuccess(Object response) {
                            List<String> distances = (List<String>) response;
                            for (int i = 0; i < distances.size(); i++) {
                                stores.get(i).setDistanceToCurrentPos(distances.get(i));
                            }

                            CustomerStoreAdapter arrayAdapter = new CustomerStoreAdapter(
                                    StoreGroupLocationActivity.this,
                                    R.layout.store_row_item,
                                    stores);

                            lvStores.setAdapter(arrayAdapter);
                            myProgress.dismiss();
                        }

                        @Override
                        public void onError(Object ex) {
                            myProgress.dismiss();
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "No stores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Object ex) {
                myProgress.dismiss();
            }
        });
    }
}

