package com.example.azkar.data.LocationProvider;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.azkar.data.sharedpreferences.PrayersPreferences;

public class AddressResultReceiver extends ResultReceiver {
    PrayersPreferences preferences;
    Context context;
    ProgressBar progressBar;

    public AddressResultReceiver(Handler handler, Context context, ProgressBar progressBar) {
        super(handler);
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == Constants.SUCCESS_RESULT) {
            preferences = new PrayersPreferences(context);
            String country = resultData.getString(Constants.COUNTRY);
            String city = resultData.getString(Constants.STATE);
            String countryCode_KEY = resultData.getString(Constants.CountryCode);
            String Latitude = resultData.getString(Constants.Latitude);
            String Longitude = resultData.getString(Constants.Longitude);

            //  Toast.makeText(context, "" + city+" "+countryCode_KEY, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "" + city + " " + countryCode_KEY, Toast.LENGTH_SHORT).show();

            preferences.setCity(city);
            preferences.setCountryCode_KEY(countryCode_KEY);
            preferences.setCountry(country);
            preferences.setLatitude(Latitude);
            preferences.setLongitude(Longitude);


        }
        progressBar.setVisibility(View.GONE);

    }
}
