package com.example.azkar.ui.PrayerTime;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.azkar.data.sharedpreferences.PrayersPreferences;
import com.example.azkar.databinding.FragmentPrayerTimeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PrayerTimeFragment extends Fragment {

    private FragmentPrayerTimeBinding binding;

    PrayersPreferences preferences;
    PrayerTimeViewModel prayerTimeViewModel;

    TextView Fajr, FajrTime, Dhuhr, DhuhrTime, Asr, AsrTime,
            Maghrib, MaghribTime, Isha, IshaTime, tv_date, countdown, prayerName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        prayerTimeViewModel =
                new ViewModelProvider(this).get(PrayerTimeViewModel.class);

        binding = FragmentPrayerTimeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = new PrayersPreferences(getContext());
        bind();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moveToShowPrayerFragment();

        String country = preferences.getCountryCode_KEY();


        double latitude = Double.parseDouble(preferences.getLatitude());
        double longitud = Double.parseDouble(preferences.getLongitude());

       // double latitude =31.25369 ;
      //  double longitud = 30.1535;

        Toast.makeText(getContext(), "gggg"+country, Toast.LENGTH_SHORT).show();

        prayerTime(prayerTimeViewModel.getPrayersT(getContext(), latitude, longitud, country));
        prayerTimeViewModel.countdown(prayerTimeViewModel.getPrayers(getContext(), latitude, longitud, country),
                prayerTimeViewModel.getNextDayPrayers(getContext(), latitude, longitud, country),
                getContext());
        tv_date.setText(prayerTimeViewModel.date);

        prayerTimeViewModel.mutableLiveData.observe(getViewLifecycleOwner(), strings -> {
            prayerName.setText(strings[0]);

            start(prayerTimeViewModel.countDownTimer(strings[1]));
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void bind() {
        Fajr = binding.Fajr;
        FajrTime = binding.FajrTime;

        Dhuhr = binding.dhuhr;
        DhuhrTime = binding.dhuhrTime;

        Asr = binding.Asr;
        AsrTime = binding.AsrTime;

        Maghrib = binding.maghrib;
        MaghribTime = binding.maghribTime;

        Isha = binding.Isha;
        IshaTime = binding.IshaTime;

        tv_date = binding.tvDate;

        countdown = binding.countdown;
        prayerName = binding.prayerName;


    }

    public void prayerTime(Date[] prayers) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        Fajr.setText("الفجر");
        FajrTime.setText(format.format(prayers[0]));

        Dhuhr.setText("الظهر");
        DhuhrTime.setText(format.format(prayers[2]));

        Asr.setText("العصر");
        AsrTime.setText(format.format(prayers[3]));

        Maghrib.setText("المغرب");
        MaghribTime.setText(format.format(prayers[4]));

        Isha.setText("العشاء");
        IshaTime.setText(format.format(prayers[5]));

        //   tv_date.setText(format.format(prayers[0]));
    }

    public void start(Long pr) {
/*
        Calendar calendar = Calendar.getInstance();
        long customTime = calendar.getTimeInMillis();

        long prayerTime = getTimeInMillis(Integer.parseInt(convertATimeTOHour(hourr)), Integer.parseInt(convertATimeTOMIN(hourr)), 0);
        // long prayerTime = getTimeInMillis(3,35 , 0);

        long pr = (prayerTime - customTime);
*/
        // textView10.setText(getDate(pr) + "");

        // countdown.setText(prayerTime + ""+":"+customTime);


        //countdown.setText(getTimeInMillis(Integer.parseInt(convertATimeTOHour(hourr)), Integer.parseInt(convertATimeTOMIN(hourr)), 0)+"");
        CountDownTimer ym = new CountDownTimer(pr, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                countdown.setText(getDate(l));
                // countdown.setText(getDate(pr) + "");

            }

            @Override
            public void onFinish() {
                // countdown.setText(20 + "");

            }
        }.start();
    }

    @SuppressLint("DefaultLocale")
    private static String getDate(Long l) {

        long totalSeconds = l / 1000;
        long currentSecond = totalSeconds % 60;

        long totalMinutes = totalSeconds / 60;

        long currentMinute = totalMinutes % 60;

        long totalHours = totalMinutes / 60;


        long currentHour = totalHours % 24;

        return String.format("%02d:%02d:%02d", currentHour,

                currentMinute,
                currentSecond,
                Locale.getDefault()

        );
    }

    private void moveToShowPrayerFragment() {
        tv_date.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();

            int yearCal = calendar.get(Calendar.YEAR);
            int monthCal = calendar.get(Calendar.MONTH);
            int mD = calendar.get(Calendar.DATE);

            DatePickerDialog pickerDialog = new DatePickerDialog(getContext(),
                    (datePicker, i, i1, i2) -> {

//                                    ShowPrayerTimeFragment myFragment = new ShowPrayerTimeFragment();
//                                    Bundle bundle = new Bundle();
//
//                                    bundle.putInt("day", i);
//                                    bundle.putInt("month", i1);
//                                    bundle.putInt("year", i2);
//
//                                    myFragment.setArguments(bundle);
//
//                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                                    FragmentTransaction fragmentTransaction = fragmentManager
//                                            .beginTransaction().
//                                            replace(R.id.fragmentContainerView2,
//                                                    myFragment).addToBackStack(null);
//
//                                    fragmentTransaction.commit();

//                        Intent intent = new Intent(getApplicationContext(), PrayAct.class);
//                        intent.putExtra("day", i);
//                        intent.putExtra("month", i1);
//                        intent.putExtra("year", i2);
//                        startActivity(intent);

                        //textView10.setText(i + "/" + i1 + "/" + i2);
                    }, yearCal, monthCal, mD);
            pickerDialog.show();

        });
    }
}