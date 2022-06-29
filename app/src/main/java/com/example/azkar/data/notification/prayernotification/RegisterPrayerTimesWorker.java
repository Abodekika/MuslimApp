package com.example.azkar.data.notification.prayernotification;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.azkar.data.prayertimecalculation.PrayerTimes;
import com.example.azkar.data.sharedpreferences.PrayersPreferences;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class RegisterPrayerTimesWorker extends Worker {

    private SimpleDateFormat format;
    PrayersPreferences preferences;
    AzanNotification azanNotification;
    OneTimeWorkRequest registerPrayerRequest;

    public RegisterPrayerTimesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        preferences = new PrayersPreferences(getApplicationContext());


        format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());


        format = new SimpleDateFormat("hh:mm a", Locale.getDefault());


        String tag_fajer = "الفجر ت ";
        WorkManager.getInstance(getApplicationContext())
                .enqueueUniqueWork(tag_fajer,
                        ExistingWorkPolicy.REPLACE,
                        registerPrayerRequest("dddd", "صلاة الفجر", "حي علس الصلاة", 0));

        String tag_th = "الظهر ت ";
        WorkManager.getInstance(getApplicationContext())
                .enqueueUniqueWork(tag_th,
                        ExistingWorkPolicy.REPLACE,
                        registerPrayerRequest("dddd", "صلاة الظهر", "حي علس الصلاة", 2));

        String tag_ِaser = "العصر ت ";
        WorkManager.getInstance(getApplicationContext())
                .enqueueUniqueWork(tag_ِaser,
                        ExistingWorkPolicy.REPLACE,
                        registerPrayerRequest("dddd", "صلاة العصر", "حي علس الصلاة", 3));

        String tag_ِmagrib = "المغرب ت ";

        WorkManager.getInstance(getApplicationContext())
                .enqueueUniqueWork(tag_ِmagrib,
                        ExistingWorkPolicy.REPLACE,
                        registerPrayerRequest("dddd", "صلاة المغرب", "حي علس الصلاة", 4));

        String tag_ِaha = "العشا ت ";
        WorkManager.getInstance(getApplicationContext())
                .enqueueUniqueWork(tag_ِaha,
                        ExistingWorkPolicy.REPLACE,
                        registerPrayerRequest("dddd", "صلاة العشاء", "حي علس الصلاة", 5));


        return Result.success();
    }


    private long calculatePrayerDelay(int year, int month, int day, Date prayer) {
        String pattern = "yyyy/MM/dd HH:mm";
        DecimalFormat decimalFormat = new DecimalFormat("00");
        format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        String time = format.format(prayer);

        String prayerDate = "" + year + "/" + decimalFormat.format(month) + "/" + decimalFormat.format(day) + " " + time;
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());

        try {
            long tim;
            Date date = format.parse(prayerDate);
            long currentTime = System.currentTimeMillis();
            Log.d("TAG", "calculatePrayerDelay: " + date.toString());
            Log.d("TAG", "calculatePrayerDelay: diff = " + (date.getTime() - currentTime) + " " + date.getTime());

            Log.d("TAG", "calculatePrayerDelay: diff11 = " + (date.getTime() - currentTime) + " " + Math.abs(date.getTime() - currentTime));

            tim = (date.getTime() - currentTime);
            // Math.abs(date.getTime() - currentTime);


                return (date.getTime() - currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

    }


    private OneTimeWorkRequest registerPrayerRequest(String tag, String title, String more, int d) {

        double latitude = Double.parseDouble(preferences.getLatitude());
        double longitud = Double.parseDouble(preferences.getLongitude());

        Calendar calendar = Calendar.getInstance();
        String country = preferences.getCountryCode_KEY();
        int dst = calendar.getTimeZone().getDSTSavings();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Date[] prayers = new PrayerTimes(day
                , month,
                year,
                latitude,
                longitud,
                getTimeZone(), (dst > 0),
                PrayerTimes.getDefaultMazhab(country),
                PrayerTimes.getDefaultWay(country)).get();


        registerPrayerRequest =
                new OneTimeWorkRequest
                        .Builder(AzanNotification.class)
                        .addTag(tag)//calculatePrayerDelay(2022,month,day,prayers[D])
                        .setInitialDelay(calculatePrayerDelay(year, month, day, prayers[d]), TimeUnit.MILLISECONDS)
                        .setInputData(getInputData_Re(title, more))
                        .build();


        return registerPrayerRequest;

    }

    public Data getInputData_Re(String title, String more) {

        Data input = new Data.Builder()
                .putString("Title", title)
                .putString("Con", more)
                .build();
        return input;
    }

    public double getTimeZone() {


        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = mTimeZone.getRawOffset();

        Double timeZone = (double) TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS);

        return timeZone;
    }


/*

        for (int D = 0; D <= prayers.length; D++) {

            switch (D) {
                case 0:
                    input = new Data.Builder()
                            .putString("Title", "صلاة الفجر")
                            .putString("Con", "الصلاة خير من النوم ")
                            .build();
                    Log.d("TAG", "calculatePrayerDelayAhmed: " + format.format(prayers[D]));
                    break;
                case 1:
                    input = new Data.Builder()
                            .putString("Title", "الشروق ")
                            .putString("Con", "حي علي الصلاة")
                            .build();
                    Log.d("TAG", "calculatePrayerDelayAhmed: " + format.format(prayers[D]));
                    break;
                case 2:
                    input = new Data.Builder()
                            .putString("Title", "الظهر ")
                            .putString("Con", "حي علي الصلاة الظهر")
                            .build();
                    Log.d("TAG", "calculatePrayerDelayAhmed: " + format.format(prayers[D]));
                    break;
                case 3:
                    input = new Data.Builder()
                            .putString("Title", "صلاة العصر ")
                            .putString("Con", "حي علي الصلاة العصر")
                            .build();
                    Log.d("TAG", "calculatePrayerDelayAhmed: " + format.format(prayers[D]));
                    break;
                case 4:
                    input = new Data.Builder()
                            .putString("Title", "صلاة المغرب ")
                            .putString("Con", "حي علي الصلاة المغرب")
                            .build();
                    Log.d("TAG", "calculatePrayerDelayAhmed: " + format.format(prayers[D]));
                    break;
                case 5:
                    input = new Data.Builder()
                            .putString("Title", "صلاة العشاء ")
                            .putString("Con", "حي علي الصلاة العشاء")
                            .build();
                    Log.d("TAG", "calculatePrayerDelayAhmed: " + format.format(prayers[D]));
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: ");
            }


        }
 */
}

