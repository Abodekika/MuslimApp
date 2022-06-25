package com.example.azkar.data.LocationProvider;


import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

public class FetchAddressIntentServices extends IntentService {

    ResultReceiver resultReceiver;


    public FetchAddressIntentServices() {
        super("FetchAddressIntentServices");
    }

    double Latitude, Longitude;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            String errormessgae = "";
            resultReceiver = intent.getParcelableExtra(Constants.RECEVIER);
            Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);

            setLatitude(location.getLatitude());
            setLongitude(location.getLongitude());


            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        1);
            } catch (Exception ioException) {
                Log.e("", "Error in getting address for the location");
            }

            if (addresses == null || addresses.size() == 0) {
                errormessgae = "No address found for the location";
                Toast.makeText(this, "" + errormessgae, Toast.LENGTH_SHORT).show();
            } else {
                Address address = addresses.get(0);
                String str_countryCode = address.getCountryCode();
                String str_Country = address.getCountryName();
                String str_state = address.getAdminArea();
                String str_district = address.getSubAdminArea();
                String str_locality = address.getLocality();
                String str_address = address.getFeatureName();

                String latitude = String.valueOf(address.getLatitude());
                String longitude = String.valueOf(address.getLongitude());


                devliverResultToRecevier(Constants.SUCCESS_RESULT,
                        str_address, str_locality, str_district, str_state,
                        str_Country, str_countryCode,latitude,longitude);


            }

            // gittimis();

        }

    }

    private void devliverResultToRecevier(int resultcode,
                                          String address,
                                          String locality,
                                          String district,
                                          String state,
                                          String country,
                                          String str_countryCode,String latitude,String longitude) {

        Bundle bundle = new Bundle();

        bundle.putString(Constants.ADDRESS, address);
        bundle.putString(Constants.LOCAITY, locality);
        bundle.putString(Constants.DISTRICT, district);
        bundle.putString(Constants.STATE, state);
        bundle.putString(Constants.COUNTRY, country);

        bundle.putString(Constants.CountryCode, str_countryCode);
        bundle.putString(Constants.Latitude, latitude);
        bundle.putString(Constants.Longitude, longitude);

        resultReceiver.send(resultcode, bundle);
    }


    /*
        private void gittimis() {
            try {
                Response<PrayerApiResponse> apiResponse = PrayerTimeViewModel.getPrayers("Egypt", "Cairo", 5, 3, 2022).
                        execute();

                List<Datum> data = apiResponse.body().getData();

                data.forEach(datum -> {
                    Timings timings = datum.getTimings();
                    String timestamp = datum.getDate().getTimestamp();
                    ArrayList<PrayerTiming> prayers = convertFromTimings(timings);
                    Toast.makeText(this, "محمد بيسلم عليك", Toast.LENGTH_SHORT).show();
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    */



}