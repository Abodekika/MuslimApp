package com.example.azkar.ui.PrayerTime;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.azkar.data.prayertimecalculation.Calculators;
import com.example.azkar.data.prayertimecalculation.PrayerTimeCalculator;
import com.example.azkar.data.prayertimecalculation.PrayerTimes;
import com.example.azkar.data.prayertimecalculation.calendar.HGDate;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class PrayerTimeViewModel extends ViewModel {

    private static final String TAG = "PrayerTimeViewModel";

    MutableLiveData<String[]> mutableLiveData = new MutableLiveData<>();
    List<Integer> ShowPrayerTime = new ArrayList<>();

    public List<Integer> getShowPrayerTime() {
        return ShowPrayerTime;
    }

    public void setShowPrayerTime(List<Integer> showPrayerTime) {
        ShowPrayerTime = showPrayerTime;
    }

    Calendar calendar = Calendar.getInstance();


    int year = calendar.get(Calendar.YEAR);
    int month = (calendar.get(Calendar.MONTH)) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    int timeZone = calendar.getTimeZone().getRawOffset() / (1000 * 60 * 60);
    int dst = calendar.getTimeZone().getDSTSavings();

    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


    public Date[] getPrayersT(Context context, double latitude, double longitud, String country) {

        Date[] prayers = new PrayerTimes(day
                , month,
                year,
                latitude,
                longitud,
                getTimeZone(), (dst > 0),
                PrayerTimes.getDefaultMazhab(country),
                PrayerTimes.getDefaultWay(country)).get();
        Toast.makeText(context, "dddd" + day + month + year, Toast.LENGTH_SHORT).show();

        return prayers;
    }

    public double[] getPrayers(Context context, double latitude, double longitud, String country) {

        double[] prayers;

        prayers = new PrayerTimeCalculator(
                day
                , month,
                year, latitude, longitud
                , timeZone, defaultMazhab(country)
                , defaultWay(country), dst
                , context).calculateDailyPrayers_withSunset();


        return prayers;
    }

    public double[] getNextDayPrayers(Context context, double latitude, double longitud, String country) {
        Calendar calendar = Calendar.getInstance();
        double[] nextDayPrayers;
        int timeZone = calendar.getTimeZone().getRawOffset() / (1000 * 60 * 60);
        int dst = calendar.getTimeZone().getDSTSavings();


        //get current islamic and georgian dates


        //set current dates


        //get saved location information

        HGDate nextDay = new HGDate();
        nextDay.nextDay();
        nextDayPrayers = new PrayerTimeCalculator(
                day + 1
                , month
                , year
                , latitude, longitud
                , timeZone, defaultMazhab(country)
                , defaultWay(country), dst
                , context).calculateDailyPrayers_withSunset();

        return nextDayPrayers;
    }

    public MutableLiveData<String[]> countdown(double[] prayers, double[] nextDayPrayers, Context context) {

        String[] t = new String[2];
        Calendar c = Calendar.getInstance();
        int houreNow = c.get(Calendar.HOUR_OF_DAY);
        int minsNow = c.get(Calendar.MINUTE);
        int counter = 0;

        for (double pray : prayers) {
            counter++;
            if (houreNow < Calculators.extractHour(pray)) {
                break;
            } else {
                if (houreNow == Calculators.extractHour(pray)) {
                    if (minsNow < Calculators.extractMinutes(pray)) {
                        break;
                    }
                }
            }
        }
        String v;
        String time;
        //switch to check the next prayer
        switch (counter) {


            case 1:


                time = "باقي علي صلاة الفجر";
                //
                v = Calculators.convertNumberType1(Calculators.extractPrayTime(context, prayers[0]));
                countDownTimer(v);

                //  prayerName.setText("fajr_prayer1" + v+v1);

                break;
            case 2:
                time = "باقي علي شروق الشمس";

                // countdown.setText(Calculators.extractPrayTime(context, prayers[1]));
                v = Calculators.convertNumberType1(Calculators.extractPrayTime(context, prayers[1]));

                countDownTimer(v);
                break;
            case 3:

                time = "باقي علي صلاة الظهر";
                v = Calculators.convertNumberType1(Calculators.extractPrayTime(context, prayers[2]));

                countDownTimer(v);
                break;
            case 4:

                time = "باقي علي صلاةالعصر";
                v = Calculators.convertNumberType1(Calculators.extractPrayTime(context, prayers[3]));

                countDownTimer(v);
                break;
            case 5:
                time = "باقي علي صلاة المغرب";
                v = Calculators.convertNumberType1(Calculators.extractPrayTime(context, prayers[4]));

                countDownTimer(v);
                break;
            case 6:
                time = "باقي علي صلاة العشاء";
                v = Calculators.convertNumberType1(Calculators.extractPrayTime(context, prayers[5]));

                countDownTimer(v);
                break;
            case 7:

                time = "باقي علي صلاة الفجر";

                v = Calculators.convertNumberType1(Calculators.extractPrayTime(context, nextDayPrayers[0]));
                countDownTimer(v);
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + counter);
        }
        t[0] = time;
        t[1] = v;

        System.out.println("Ahmeeddd" + t[0] + t[1]);

        mutableLiveData.setValue(t);
        return mutableLiveData;

    }


    public long countDownTimer(String hourr) {

        Calendar calendar = Calendar.getInstance();
        long customTime = calendar.getTimeInMillis();

        long prayerTime = getTimeInMillis(Integer.parseInt(convertATimeTOHour(hourr)), Integer.parseInt(convertATimeTOMIN(hourr)), 0);
        // long prayerTime = getTimeInMillis(3,35 , 0);

        long pr = (prayerTime - customTime);

        return pr;
    }

    private static String convertATimeTOHour(String hourr) {
        String h = String.valueOf(hourr.charAt(0));
        String h1 = String.valueOf(hourr.charAt(1));
        String hour = h.concat(h1);


        System.out.println(hour);
        System.out.println("ho Ahmed" + hour);
        return hour;
    }

    private static String convertATimeTOMIN(String hourr) {

        String m = String.valueOf(hourr.charAt(3));
        String m1 = String.valueOf(hourr.charAt(4));
        String min = m.concat(m1);
        System.out.println("min Ahmed" + min);
        return min;
    }

    public static long getTimeInMillis(int hour, int min, int sec) {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());


        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("min Ahmed " + day + " " + month + " " + year);

        calendar.set(year, month, day, hour, min, sec);
        return calendar.getTimeInMillis();
    }

    public static int defaultMazhab(String countryCode) {
        int mazhab = 0;
        switch (PrayerTimes.getDefaultMazhab(countryCode)) {
            case PTC_MAZHAB_HANAFI:
                mazhab = 1;
                break;
            case PTC_MAZHAB_SHAFEI:
                mazhab = 0;
                break;
        }
        return mazhab;
    }


    public static int defaultWay(String countryCode) {
        int way = 0;
        switch (PrayerTimes.getDefaultWay(countryCode)) {
            case PTC_WAY_EGYPT:
                way = 0;
                break;
            case PTC_WAY_UMQURA:
                way = 3;
                break;

            case PTC_WAY_MWL:
                way = 4;
                break;

            case PTC_WAY_KARACHI:
                way = 1;
                break;

            case PTC_WAY_ISNA:
                way = 2;
                break;
        }

        return way;
    }


    public Date[] getPrayersTT(double latitude, double longitud, String country, Context context) {
        Date fajrDate, sunriseDate, duhrDate, asrDate, maghrebDate, ishaDate, midNightDate;
        PrayerTimes prayerTimes;
        Calendar mid = Calendar.getInstance();
        mid.set(Calendar.HOUR_OF_DAY, 0);
        mid.set(Calendar.MINUTE, 0);
        mid.set(Calendar.SECOND, 0);



        prayerTimes = new PrayerTimes(day, month + 1, year, latitude, longitud, timeZone, (dst > 0), PrayerTimes.getDefaultMazhab(country), PrayerTimes.getDefaultWay(country));

        Date[] dates = prayerTimes.get();

        return dates;
    }

    public double getTimeZone() {


        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = mTimeZone.getRawOffset();
        Double timeZone = Double.valueOf(TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS));

        return timeZone;
    }
}
