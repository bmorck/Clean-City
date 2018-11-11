package chukuzoegwu.cleancity;
/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link android.Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
public class MapView_Activity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    // Button to claim a tile
    protected Button claimButton;

    // Tile selected
    protected boolean tileSelected;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private GoogleMapOptions mMapOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_views);

        claimButton = findViewById(R.id.claimButton);
        tileSelected = false;

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this, Locale.ENGLISH);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);

            // Gets and zooms to the current location of the user
            double lat = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
            double lon = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
            LatLng newLatLng = new LatLng(lat, lon);
            CameraPosition cp = CameraPosition.fromLatLngZoom(newLatLng, 18);
            CameraUpdate point = CameraUpdateFactory.newCameraPosition(cp);
            mMap.animateCamera(point);

            // Overlays the map with tiles
            TileProvider coordTileProvider = new chukuzoegwu.cleancity.CoordTileProvider(this.getApplicationContext());
            mMap.addTileOverlay(new TileOverlayOptions().tileProvider(coordTileProvider));
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setZoomGesturesEnabled(false);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Current Location button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        if (reverseGeocode(lat, lng, 1) != null) {
            String place = reverseGeocode(lat, lng, 1);
            Toast.makeText(this, place, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Something is wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private String reverseGeocode (double lat, double lng, int max) {
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lng, max);
        }catch (IllegalArgumentException e ){
            return null;
        }catch (IOException e1) {
            return null;
        }
        //return addresses.get(0).getAddressLine(0);
        //return addresses.get(0).getFeatureName();
        return addresses.get(0).getAddressLine(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
