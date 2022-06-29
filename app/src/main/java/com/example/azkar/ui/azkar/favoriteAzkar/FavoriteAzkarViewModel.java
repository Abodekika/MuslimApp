package com.example.azkar.ui.azkar.favoriteAzkar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.ViewModel;


import com.example.azkar.data.Pojo.azkar.Azker;
import com.example.azkar.data.Pojo.azkar.Content;
import com.example.azkar.data.Pojo.azkar.FavItem;
import com.example.azkar.data.database.azkardatabase.AzkarDataBase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAzkarViewModel extends ViewModel {


    private AzkarDataBase azkarDataBase;

    public List<Azker> loadData(Context context) {
        List<Azker> fav_azkar_list = new ArrayList<>();
        azkarDataBase = new AzkarDataBase(context);

        if (fav_azkar_list != null) {
            fav_azkar_list.clear();
        }

        SQLiteDatabase db = azkarDataBase.getReadableDatabase();
        Cursor cursor = azkarDataBase.readAllFav();

        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String count = cursor.getString(cursor.getColumnIndex("count"));
                @SuppressLint("Range") String bookmark = cursor.getString(cursor.getColumnIndex("bookmark"));
                @SuppressLint("Range") byte[] blob = cursor.getBlob(cursor.getColumnIndex("content"));
                String json = new String(blob);
                Gson gson = new Gson();
                List<Content> content = gson.fromJson(json, new TypeToken<List<Content>>() {
                }.getType());

                Azker favItem = new Azker(id, title, count, bookmark, content);

                fav_azkar_list.add(favItem);

            }
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
                db.close();
            }

        }
        return fav_azkar_list;
    }
}