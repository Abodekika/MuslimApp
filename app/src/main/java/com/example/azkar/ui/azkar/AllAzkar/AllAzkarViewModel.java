package com.example.azkar.ui.azkar.AllAzkar;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AllAzkarViewModel extends ViewModel {

    public String readAzkar(Context context) {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = context.getAssets().open("Azkar/azkar4.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }

}