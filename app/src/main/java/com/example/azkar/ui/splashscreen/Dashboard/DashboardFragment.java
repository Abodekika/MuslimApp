package com.example.azkar.ui.splashscreen.Dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import com.example.azkar.data.LocationProvider.AddressResultReceiver;
import com.example.azkar.data.LocationProvider.Constants;
import com.example.azkar.data.LocationProvider.FetchAddressIntentServices;
import com.example.azkar.data.download.download_quan_image.HTTP_ayat;
import com.example.azkar.data.sharedpreferences.PrayersPreferences;
import com.example.azkar.databinding.FragmentDashbordBinding;
import com.example.azkar.ui.MainActivity;
import com.example.azkar.ui.PrayerTime.PrayerTimeViewModel;
import com.example.azkar.ui.location.LocationActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class DashboardFragment extends Fragment {


    TextView Data, time, location;
    LinearLayout quran_liner_layout, azkar_linear_layout, pray_time_liner_layout;
    Thread thread;
    ImageView location_img;
    private FragmentDashbordBinding binding;
    PrayersPreferences preferences;
    PrayerTimeViewModel prayerTimeViewModel;
    ResultReceiver resultReceiver;

    ProgressBar des_progressBar;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashbordBinding.inflate(inflater, container, false);

        prayerTimeViewModel =
                new ViewModelProvider(this).get(PrayerTimeViewModel.class);


        View root = binding.getRoot();
        Data = binding.Data;
        time = binding.time;
        location = binding.location;
        quran_liner_layout = binding.quranLinerLayout;
        azkar_linear_layout = binding.azkarLinearLayout;
        pray_time_liner_layout = binding.prayerTimeLinearLayout;
        location_img = binding.locationImg;
        des_progressBar = binding.dasProgressBar;

        resultReceiver = new AddressResultReceiver(new Handler(), requireContext(), des_progressBar);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = new PrayersPreferences(requireContext());
        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        time.setText(date);
        int d = calendar.get(Calendar.HOUR_OF_DAY);
        int d1 = calendar.get(Calendar.MINUTE);
        int d2 = calendar.get(Calendar.SECOND);
        calendar.set(d, d1, d2);

        location.setText(preferences.getCity() + " " + preferences.getCountry());


        thread = new Thread(() -> {
            try {
                while (!thread.isInterrupted()) {
                    Thread.sleep(1);
                    requireActivity().runOnUiThread(() -> {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(" hh:mm:ss aa");
                        String formattedDate = dateFormat.format(new Date());

                        Data.setText(formattedDate);

                    });
                }
            } catch (InterruptedException e) {
            }


        });

        thread.start();
        String country = preferences.getCountryCode_KEY();


        double latitude = Double.parseDouble(preferences.getLatitude());
        double longitud = Double.parseDouble(preferences.getLongitude());


        prayerTimeViewModel.countdown(prayerTimeViewModel.getPrayers(getContext(), latitude, longitud, country),
                prayerTimeViewModel.getNextDayPrayers(getContext(), latitude, longitud, country),
                getContext());


        quran_liner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DashboardFragment.this).navigate(DashboardFragmentDirections.actionDashbordFragmentToSoraListFragment2());

            }
        });
        azkar_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DashboardFragment.this).navigate(DashboardFragmentDirections.actionDashbordFragmentToAzkarFragment());

            }
        });

        pray_time_liner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DashboardFragment.this).navigate(DashboardFragmentDirections.actionDashbordFragmentToPrayerTimeFragment());

            }
        });

        location_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();


            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void fetchAddressFromLocation(android.location.Location location) {

        Intent intent = new Intent(getContext(), FetchAddressIntentServices.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);

        preferences.setLatitude(String.valueOf(location.getLatitude()));
        preferences.setLongitude(String.valueOf(location.getLongitude()));

        //  Toast.makeText(this, location.getLatitude() + "" + location.getLongitude(), Toast.LENGTH_SHORT).show();
        getActivity().startService(intent);


    }

    private void getCurrentLocation() {

        des_progressBar.setVisibility(View.VISIBLE);

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(requireActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(requireContext())
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

                            des_progressBar.setVisibility(View.GONE);


                        }
                    }
                }, Looper.getMainLooper());


    }
}