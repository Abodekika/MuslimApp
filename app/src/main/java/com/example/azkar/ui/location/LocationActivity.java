package com.example.azkar.ui.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.azkar.R;

import com.example.azkar.data.LocationProvider.AddressResultReceiver;
import com.example.azkar.data.LocationProvider.Constants;
import com.example.azkar.data.LocationProvider.FetchAddressIntentServices;
import com.example.azkar.data.sharedpreferences.PrayersPreferences;

import com.example.azkar.ui.MainActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity {

    ResultReceiver resultReceiver;
    PrayersPreferences preferences;
    ProgressBar progressBar;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button btn_location = findViewById(R.id.btn_location);
        progressBar = findViewById(R.id.progressBar);


        resultReceiver = new AddressResultReceiver(new Handler(), getBaseContext(), progressBar);
        preferences = new PrayersPreferences(this);


        //  getCurrentLocation();

        btn_location.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LocationActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                getCurrentLocation();
            }


        });

    }

    private void fetchAddressFromLocation(android.location.Location location) {
        Intent intent = new Intent(this, FetchAddressIntentServices.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);

        preferences.setLatitude(String.valueOf(location.getLatitude()));
        preferences.setLongitude(String.valueOf(location.getLongitude()));

        Toast.makeText(this, location.getLatitude() + "" + location.getLongitude(), Toast.LENGTH_SHORT).show();
        startService(intent);

        Intent i1 = new Intent(LocationActivity.this, MainActivity.class);
        startActivity(i1);
        finish();
    }

    private void getCurrentLocation() {

        progressBar.setVisibility(View.VISIBLE);

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(LocationActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();
                            // textLatLong.setText(String.format("Latitude : %s\n Longitude: %s", lati, longi));

                            android.location.Location location = new android.location.Location("providerNA");
                            location.setLongitude(longi);
                            location.setLatitude(lati);

                            fetchAddressFromLocation(location);

                        } else {

                            progressBar.setVisibility(View.GONE);


                        }
                    }
                }, Looper.getMainLooper());


    }

}