package com.example.jun.antiphone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jun.antiphone.singleton.RestfulAPIManager;
import com.example.jun.antiphone.singleton.VolleyCallback;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import entity.StoreDTO;

public class StoreGroupLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private ProgressDialog myProgress;
    private ListView lvStores;
    private GoogleMap gmap;
    private StoreDTO[] stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_group_location);

        // Tạo Progress Bar
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Map Loading ...");
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);

        // Hiển thị Progress Bar
        myProgress.show();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);

        lvStores = findViewById(R.id.lvStores);

        RestfulAPIManager api = RestfulAPIManager.getInstancẹ̣̣̣();

        api.getStoresInStoreGroup(this, 2, new VolleyCallback() {

            @Override
            public void onSuccess(JSONArray arrays) {

                if (arrays.length() > 0) {

                    ObjectMapper mapper = new ObjectMapper();
                    stores = new StoreDTO[arrays.length()];

                    for (int i = 0; i < stores.length; i++) {
                        try {
                            StoreDTO store = mapper.readValue(arrays.get(i).toString(), StoreDTO.class);
                            setMarker(new LatLng(Double.parseDouble(store.getStoreLatitude()),
                                    Double.parseDouble(store.getStoreLongitude())));

                            stores[i] = store;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    ArrayAdapter<StoreDTO> arrayAdapter
                            = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stores);

                    lvStores.setAdapter(arrayAdapter);
                }
            }
        });

        lvStores.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatLng location = new LatLng(
                        Double.parseDouble(stores[position].getStoreLatitude()),
                        Double.parseDouble(stores[position].getStoreLongitude()));
                setMap(location);
            }

        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        gmap = googleMap;

        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gmap.getUiSettings().setZoomControlsEnabled(true);

        gmap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

            @Override
            public void onMapLoaded() {
                myProgress.dismiss();
            }
        });
    }

    private void setMarker(LatLng location) {
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        gmap.addMarker(markerOptions);
    }

    private void setMap(LatLng location) {
        CameraPosition INIT = new CameraPosition.Builder().target(location).zoom(15.5F).bearing(300F) // orientation
                .tilt(50F) // viewing angle
                .build();
        gmap.moveCamera(CameraUpdateFactory.newCameraPosition(INIT));

    }
}

