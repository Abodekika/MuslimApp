package com.example.azkar.data.prayertimecalculation;

import android.content.Context;

import com.example.azkar.R;


// Prayer
public class Calculators {

    public static int extractMinutes(double time) {
        /*Log.d("String_date_m" , time+"");
        String preparedNumber = (time + "").substring(0, 3);
        double timeDouble = new Double(time);
        int houre = (int) timeDouble;
        double minsDouble = (60 * (timeDouble - houre)) / 100;
        preparedNumber = (minsDouble + "").substring(0, (minsDouble + "").length() < 4 ? 3 : 4);
        double mins = new Double(preparedNumber);
        String minsFinal = String.valueOf(mins).replace("0.", "");
        Log.d("String_date_m" , minsFinal+"");
        return Integer.parseInt(minsFinal.trim());*/

        String preparedNumber = (time + "").substring(0, 3);
        double timeDouble = new Double(time);
        int hour = (int) timeDouble;
        double minsDouble = (60 * (timeDouble - hour)) / 100;

        preparedNumber = (minsDouble + "").substring(0, (minsDouble + "").length() < 4 ? 3 : 4);
        double mins = new Double(preparedNumber);
        String minsFinal = String.valueOf(mins).replace("0.", "");

        return Integer.parseInt((minsFinal.length() == 1 ? minsFinal + "0" : minsFinal).trim());

    }

    /**
     * Function to extract Hours only from double time
     *
     * @param time Double time
     * @return Integer hours
     */
    public static int extractHour(double time) {
        String preparedNumber = (time + "").substring(0, 3);
        double timeDouble = Double.parseDouble(preparedNumber);
        return (int) timeDouble;
    }


    /**
     * Function to extract praying time from double numbers
     *
     * @param time Double time
     * @return String Time of pray
     */
    public static String extractPrayTime(Context context, double time) {

        boolean pmFlag = false;
        String preparedNumber = (time + "").substring(0, 3);
        double timeDouble = new Double(time);
        int hour = (int) timeDouble;
        double minsDouble = (60 * (timeDouble - hour)) / 100;

        preparedNumber = (minsDouble + "").substring(0, (minsDouble + "").length() < 4 ? 3 : 4);
        double mins = new Double(preparedNumber);
        String minsFinal = String.valueOf(mins).replace("0.", "");
//
        System.out.println("Ahhhhhh" + minsFinal);
//
//        if (ConfigPreferences.getTwentyFourMode(context) != true) {
//            if (hour > 12) {
//                hour -= 12;
//                pmFlag = true;
//            }
//        }

        String h = "0" + hour + ":" +
                (minsFinal.length() == 1 ? minsFinal + "0" : minsFinal) + " " +
                (hour >= 12 ? context.getString(R.string.pm) : context.getString(R.string.am));
        String h1 = hour + ":" +
                (minsFinal.length() == 1 ? minsFinal + "0" : minsFinal) + " " +
                (hour >= 12 ? context.getString(R.string.pm) : context.getString(R.string.am));
//        return convertNumberType(context, hour + ":" +
//                (minsFinal.length() == 1 ? minsFinal + "0" : minsFinal) + " " +
//                ((hour >= 12 || pmFlag) ? context.getString(R.string.pm) : context.getString(R.string.am)));

        return hour >= 7 ? h1 : h;
    }

    public static String convertNumberType(Context context, String number) {

        try {
            if (context.getResources().getConfiguration().locale.getDisplayLanguage().equals("??????????????"))
                return number.replaceAll("0", "??").replaceAll("1", "??")
                        .replaceAll("2", "??").replaceAll("3", "??")
                        .replaceAll("4", "??").replaceAll("5", "??")
                        .replaceAll("6", "??").replaceAll("7", "??")
                        .replaceAll("8", "??").replaceAll("9", "??");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public static String convertNumberType1(String number) {


        number.replace("??", "0")
                .replaceAll("??", "1")
                .replaceAll("??", "2")
                .replaceAll("??", "3")
                .replaceAll("??", "4")
                .replaceAll("??", "5")
                .replaceAll("??", "6")
                .replaceAll("??", "7")
                .replaceAll("??", "8")
                .replaceAll("??", "9");

        return number;
    }
}
