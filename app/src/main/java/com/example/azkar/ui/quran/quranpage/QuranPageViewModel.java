package com.example.azkar.ui.quran.quranpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;


import com.example.azkar.data.download.download_quan_image.HTTP_ayat;
import com.example.azkar.data.quran_manager.QuranPageManger;

import java.io.File;

public class QuranPageViewModel extends ViewModel {




    public Bitmap getQuranImage(int i) {


        return QuranPageManger.getQuranPages(i);
    }

    public void downloadQuranPage(Context context, String url) {
        File root1;


        root1 = Environment.getExternalStoragePublicDirectory("");


        File dir = new File(root1 + "/hisnmuslim/quranImage");


        String fname = "/quran_images";
        File file = new File(dir, fname);


        if (!file.exists()) {
            if (isConnected(context)) {
                final HTTP_ayat downloadTask = new HTTP_ayat(context);
                downloadTask.execute(url);
            } else {
                Toast.makeText(context, "لا يوجد انترنت ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.isConnected())
                return true;
            else
                return false;
        } else
            return false;

    }


}